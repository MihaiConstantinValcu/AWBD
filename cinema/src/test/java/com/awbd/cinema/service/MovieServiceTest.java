package com.awbd.cinema.service;

import com.awbd.cinema.domain.Genre;
import com.awbd.cinema.domain.Movie;
import com.awbd.cinema.domain.enums.Rating;
import com.awbd.cinema.dtos.GenreDto;
import com.awbd.cinema.dtos.MovieDto;
import com.awbd.cinema.exceptions.ResourceNotFoundException;
import com.awbd.cinema.repositories.GenreRepository;
import com.awbd.cinema.repositories.MovieRepository;
import com.awbd.cinema.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    @Mock
    GenreRepository genreRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    MovieService movieService;

    Movie movie;
    MovieDto movieDto;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");

        movieDto = new MovieDto();
        movieDto.setId(1L);
        movieDto.setTitle("Test Movie");
    }

    @Test
    void testFindAllPageable() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Movie> moviePage = new PageImpl<>(List.of(movie));
        when(movieRepository.findAll(pageable)).thenReturn(moviePage);
        when(modelMapper.map(movie, MovieDto.class)).thenReturn(movieDto);

        Page<MovieDto> result = movieService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        verify(movieRepository, times(1)).findAll(pageable);
    }

    @Test
    void testFindAll() {
        when(movieRepository.findAll()).thenReturn(List.of(movie));
        when(modelMapper.map(movie, MovieDto.class)).thenReturn(movieDto);

        List<MovieDto> result = movieService.findAll();

        assertEquals(1, result.size());
        assertEquals("Test Movie", result.get(0).getTitle());
    }

    @Test
    void testFindById_found() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(modelMapper.map(movie, MovieDto.class)).thenReturn(movieDto);

        MovieDto result = movieService.findById(1L);

        assertEquals("Test Movie", result.getTitle());
    }

    @Test
    void testFindById_notFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> movieService.findById(1L));
    }

    @Test
    void testFindAllGenres() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("SF");

        GenreDto genreDto = new GenreDto();
        genreDto.setId(1L);
        genreDto.setName("SF");

        when(genreRepository.findAll()).thenReturn(List.of(genre));
        when(modelMapper.map(genre, GenreDto.class)).thenReturn(genreDto);

        List<GenreDto> result = movieService.findAllGenres();
        assertEquals(1, result.size());
        assertEquals("SF", result.get(0).getName());
    }

    @Test
    void testSave() {
        movieService.save(movie);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void testUpdateMovie_found() {
        Movie updated = new Movie();
        updated.setTitle("Updated");
        updated.setDescription("New desc");
        updated.setDuration(100);
        updated.setRating(Rating.PG);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        movieService.updateMovie(1L, updated);

        assertEquals("Updated", movie.getTitle());
        assertEquals("New desc", movie.getDescription());
        assertEquals(100, movie.getDuration());
        assertEquals(Rating.PG, movie.getRating());
        verify(movieRepository).save(movie);
    }

    @Test
    void testUpdateMovie_notFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> movieService.updateMovie(1L, new Movie()));
    }
}
