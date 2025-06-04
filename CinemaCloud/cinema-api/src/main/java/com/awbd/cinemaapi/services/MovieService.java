package com.awbd.cinemaapi.services;

import com.awbd.cinemaapi.dtos.GenreDto;
import com.awbd.cinemaapi.dtos.MovieDto;
import com.awbd.cinemaapi.exceptions.ResourceNotFoundException;
import com.awbd.cinemaapi.feigns.ScreeningFeignClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final ModelMapper modelMapper;
    private final ScreeningFeignClient screeningFeignClient;

    public Page<MovieDto> findAll(Pageable pageable) {
        return screeningFeignClient.getPagedMovies(pageable);
    }

    public List<MovieDto> findAll() {
        return screeningFeignClient.getAllMovies();
    }

    public MovieDto findById(Long id) {
        try{
            return screeningFeignClient.getMovieById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Movie with id " + id + " not found");
        }
    }

    public List<GenreDto> findAllGenres() {
        return screeningFeignClient.getAllGenres();
    }

    public void save(MovieDto movie) {
        screeningFeignClient.addMovie(movie);
    }

    public void updateMovie(Long id, MovieDto updatedMovie) {

        try {
            screeningFeignClient.updateMovie(id, updatedMovie);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Movie with id " + id + " not found");
        }
    }
}
