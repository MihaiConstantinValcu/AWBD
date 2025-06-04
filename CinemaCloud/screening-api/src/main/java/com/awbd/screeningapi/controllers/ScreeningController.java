package com.awbd.screeningapi.controllers;

import com.awbd.screeningapi.dtos.HallDto;
import com.awbd.screeningapi.dtos.ScreeningDto;
import com.awbd.screeningapi.dtos.SeatDto;
import com.awbd.screeningapi.services.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScreeningController {

    private final ScreeningService screeningService;

    @GetMapping("/halls")
    public List<HallDto> getAllHalls() {
        return screeningService.findAllHalls();
    }

    @PostMapping("/screenings")
    public void saveScreening(@RequestBody ScreeningDto screening) {
        screeningService.save(screening);
    }

    @GetMapping("/screenings/movie/{movieId}")
    public List<ScreeningDto> getScreeningsByMovie(@PathVariable Long movieId) {
        return screeningService.findAllForMovie(movieId);
    }

    @GetMapping("/screenings/{id}")
    public ScreeningDto getScreeningById(@PathVariable Long id) {
        return screeningService.findScreeningById(id);
    }

    @GetMapping("/halls/{id}")
    public HallDto getHallById(@PathVariable Long id) {
        return screeningService.findHallById(id);
    }

    @GetMapping("/halls/{id}/seats")
    public List<SeatDto> getSeatsByHallId(@PathVariable Long id) {
        return screeningService.findAllByHallId(id);
    }

    @GetMapping("/seats/{id}")
    public SeatDto getSeatById(@PathVariable Long id){
        return screeningService.findSeatById(id);
    }

    @PostMapping("/seats/list")
    public List<SeatDto> getSeatsByIdIn(@RequestBody List<Long> ids) {
        return screeningService.findAllByIdIn(ids);
    }
}