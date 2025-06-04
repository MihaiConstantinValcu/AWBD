package com.awbd.screeningapi.services;

import com.awbd.screeningapi.domain.Genre;
import com.awbd.screeningapi.domain.Movie;
import com.awbd.screeningapi.dtos.GenreDto;
import com.awbd.screeningapi.dtos.MovieDto;
import com.awbd.screeningapi.repositories.GenreRepository;
import com.awbd.screeningapi.repositories.MovieRepository;
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
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    public Page<MovieDto> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .map(movie -> modelMapper.map(movie, MovieDto.class));
    }

    public List<MovieDto> findAll() {
        return movieRepository.findAll().stream()
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .toList();
    }

    public MovieDto findById(Long id) {
        return movieRepository.findById(id)
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .orElseThrow();
    }

    public List<GenreDto> findAllGenres() {
        return genreRepository.findAll().stream()
                .map(genre -> modelMapper.map(genre, GenreDto.class))
                .toList();
    }

    public void save(MovieDto movie) {
        movieRepository.save(modelMapper.map(movie, Movie.class));
    }

    public void updateMovie(Long id, MovieDto updatedMovie) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow();

        movie.setTitle(updatedMovie.getTitle());
        movie.setDescription(updatedMovie.getDescription());
        movie.setDuration(updatedMovie.getDuration());
        movie.setRating(updatedMovie.getRating());
        movie.setGenre(modelMapper.map(updatedMovie.getGenre(), Genre.class));

        movieRepository.save(movie);
    }
}
