package com.awbd.cinema.services;

import com.awbd.cinema.domain.Movie;
import com.awbd.cinema.dtos.GenreDto;
import com.awbd.cinema.dtos.MovieDto;
import com.awbd.cinema.repositories.GenreRepository;
import com.awbd.cinema.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

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

    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    public void updateMovie(Long id, Movie updatedMovie) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow();

        movie.setTitle(updatedMovie.getTitle());
        movie.setDescription(updatedMovie.getDescription());
        movie.setDuration(updatedMovie.getDuration());
        movie.setRating(updatedMovie.getRating());
        movie.setGenre(updatedMovie.getGenre());

        movieRepository.save(movie);
    }
}
