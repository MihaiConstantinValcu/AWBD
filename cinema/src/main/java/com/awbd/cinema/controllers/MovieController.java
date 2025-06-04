package com.awbd.cinema.controllers;

import com.awbd.cinema.domain.Movie;
import com.awbd.cinema.dtos.GenreDto;
import com.awbd.cinema.services.MovieService;
import com.awbd.cinema.services.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieController {
    private final ScreeningService screeningService;
    private final MovieService movieService;

    @GetMapping("/movies/{id}")
    public String showMovieDetails(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.findById(id));
        model.addAttribute("genres", movieService.findAllGenres());
        model.addAttribute("screenings", screeningService.findAllForMovie(id));

        return "movie_details";
    }

    @PostMapping("/movies/update/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute("movie") Movie movie) {
        movieService.updateMovie(id, movie);

        return "redirect:/";
    }

    @RequestMapping("/movie_form")
    public String showAddMovie(Model model) {
        List<GenreDto> genres = movieService.findAllGenres();
        model.addAttribute("genres", genres);
        model.addAttribute("movie", new Movie());

        return "admin/movie_form";
    }

    @PostMapping("/movies/add")
    public String addMovie(@ModelAttribute("movie") Movie movie) {
        movieService.save(movie);
        return "redirect:/";
    }
}
