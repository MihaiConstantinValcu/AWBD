package com.awbd.cinema.controller;

import com.awbd.cinema.domain.Movie;
import com.awbd.cinema.dtos.GenreDto;
import com.awbd.cinema.dtos.MovieDto;
import com.awbd.cinema.exceptions.ResourceNotFoundException;
import com.awbd.cinema.services.MovieService;
import com.awbd.cinema.services.ScreeningService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Profile("postgres")
public class MovieControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MovieService movieService;

    @MockBean
    ScreeningService screeningService;

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testShowMovieDetails() throws Exception {
        Long movieId = 1L;

        when(movieService.findById(movieId)).thenReturn(new MovieDto());
        when(movieService.findAllGenres()).thenReturn(Collections.emptyList());
        when(screeningService.findAllForMovie(movieId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/movies/{id}", movieId))
                .andExpect(status().isOk())
                .andExpect(view().name("movie_details"))
                .andExpect(model().attributeExists("movie", "genres", "screenings"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void  testUpdateMovie() throws Exception {
        mockMvc.perform(post("/movies/update/{id}", 1L)
                        .param("title", "Test Movie")
                        .param("description", "Test Description")
                        .param("duration", "120")
                        .param("rating", "PG")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(movieService, times(1)).updateMovie(eq(1L), any(Movie.class));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void  testShowAddMovieForm() throws Exception {
        when(movieService.findAllGenres()).thenReturn(Collections.singletonList(new GenreDto()));

        mockMvc.perform(get("/movie_form"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/movie_form"))
                .andExpect(model().attributeExists("genres", "movie"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void  testAddMovie() throws Exception {
        mockMvc.perform(post("/movies/add")
                        .param("title", "New Movie")
                        .param("description", "New description")
                        .param("duration", "90")
                        .param("rating", "G")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<Movie> movieCaptor = ArgumentCaptor.forClass(Movie.class);
        verify(movieService, times(1)).save(movieCaptor.capture());

        Movie saved = movieCaptor.getValue();
        assertEquals("New Movie", saved.getTitle());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void  movieByIdNotFound() throws Exception {
        Long id = 100L;

        when(movieService.findById(id)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/movies/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(view().name("exceptions/notFoundException"))
                .andExpect(model().attributeExists("exception"));
    }
}
