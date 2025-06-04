package com.awbd.screeningapi.controllers;

import com.awbd.screeningapi.domain.Movie;
import com.awbd.screeningapi.dtos.GenreDto;
import com.awbd.screeningapi.dtos.MovieDto;
import com.awbd.screeningapi.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> getAllMovies() {
        return movieService.findAll();
    }

    @GetMapping("/paged")
    public Page<MovieDto> getPagedMovies(@PageableDefault Pageable pageable) {
        return movieService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public MovieDto getMovieById(@PathVariable Long id) {
        return movieService.findById(id);
    }

    @PostMapping
    public void addMovie(@RequestBody MovieDto movie) {
        movieService.save(movie);
    }

    @PutMapping("/{id}")
    public void updateMovie(@PathVariable Long id, @RequestBody MovieDto movie) {
        movieService.updateMovie(id, movie);
    }

    @GetMapping("/genres")
    public List<GenreDto> getAllGenres() {
        return movieService.findAllGenres();
    }
}
