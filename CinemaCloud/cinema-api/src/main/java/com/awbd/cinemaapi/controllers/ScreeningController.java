package com.awbd.cinemaapi.controllers;

import com.awbd.cinemaapi.dtos.MovieDto;
import com.awbd.cinemaapi.dtos.ScreeningDto;
import com.awbd.cinemaapi.dtos.SeatDto;
import com.awbd.cinemaapi.dtos.TicketDto;
import com.awbd.cinemaapi.services.MovieService;
import com.awbd.cinemaapi.services.ScreeningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ScreeningController {
    private final ScreeningService screeningService;
    private final MovieService movieService;

    @RequestMapping("/screening-form")
    public String showScreeningForm(@RequestParam(required = false) Long movieId, Model model) {
        ScreeningDto screening = new ScreeningDto();
        if (movieId != null) {
            MovieDto movie = movieService.findById(movieId);
            screening.setMovie(movie);
        }

        model.addAttribute("screening", screening);
        model.addAttribute("halls", screeningService.findAllHalls());
        model.addAttribute("movies", movieService.findAll());

        return "admin/screening_form";
    }

    @PostMapping("/screenings/add")
    public String addScreening(@Valid @ModelAttribute("screening") ScreeningDto screening) {
        screeningService.save(screening);
        log.info("Screening added: {}", screening.getStartTime().toString());
        return "redirect:/";
    }

    @RequestMapping("/screenings/reserve/{id}")
    public String showReserveForm(@PathVariable Long id, Model model) {
        List<SeatDto> availableSeats = screeningService.getAvailableSeatsForScreening(id);
        TicketDto ticket = new TicketDto();
        ticket.setScreeningId(id);
        ScreeningDto screening = screeningService.findById(id);

        model.addAttribute("screening", screening);
        model.addAttribute("ticket", ticket);
        model.addAttribute("availableSeats", availableSeats);
        return "user/reserve";
    }
}
