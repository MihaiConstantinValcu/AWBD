package com.awbd.cinemaapi.feigns;


import com.awbd.cinemaapi.dtos.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "screening-api")
public interface ScreeningFeignClient {

    @GetMapping("/api/halls")
    List<HallDto> getAllHalls();

    @GetMapping("/api/screenings/movie/{movieId}")
    List<ScreeningDto> getScreeningsForMovie(@PathVariable Long movieId);

    @GetMapping("/api/screenings/{id}")
    ScreeningDto getScreeningById(@PathVariable Long id);

    @PostMapping("/api/screenings")
    void saveScreening(@RequestBody ScreeningDto screening);

    @GetMapping("/api/halls/{id}")
    HallDto getHallById(@PathVariable Long id);

    @GetMapping("/api/halls/{id}/seats")
    List<SeatDto> getSeatsByHallId(@PathVariable Long id);

    @PostMapping("/api/seats/list")
    List<SeatDto> getSeatsByIdIn(@RequestBody List<Long> ids);

    @GetMapping("/api/seats/{id}")
    SeatDto getSeatById(@PathVariable Long id);

    @GetMapping("/api/movies")
    List<MovieDto> getAllMovies();

    @GetMapping("/api/movies/paged")
    Page<MovieDto> getPagedMovies(Pageable pageable);

    @GetMapping("/api/movies/{id}")
    MovieDto getMovieById(@PathVariable Long id);

    @PostMapping("/api/movies")
    void addMovie(@RequestBody MovieDto movie);

    @PutMapping("/api/movies/{id}")
    void updateMovie(@PathVariable Long id, @RequestBody MovieDto movie);

    @GetMapping("/api/movies/genres")
    List<GenreDto> getAllGenres();
}