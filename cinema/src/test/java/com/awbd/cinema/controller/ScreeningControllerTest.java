package com.awbd.cinema.controller;

import com.awbd.cinema.domain.Screening;
import com.awbd.cinema.dtos.MovieDto;
import com.awbd.cinema.dtos.ScreeningDto;
import com.awbd.cinema.dtos.HallDto;
import com.awbd.cinema.services.MovieService;
import com.awbd.cinema.services.ScreeningService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Profile("postgres")
public class ScreeningControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MovieService movieService;

    @MockBean
    ScreeningService screeningService;

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testShowScreeningForm_withMovieId() throws Exception {
        Long movieId = 1L;
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movieId);
        when(movieService.findById(movieId)).thenReturn(movieDto);
        when(screeningService.findAllHalls()).thenReturn(Collections.emptyList());
        when(movieService.findAll()).thenReturn(Collections.singletonList(movieDto));

        mockMvc.perform(get("/screening-form").param("movieId", movieId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/screening_form"))
                .andExpect(model().attributeExists("screening", "halls", "movies"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testShowScreeningForm_withoutMovieId() throws Exception {
        when(screeningService.findAllHalls()).thenReturn(Collections.emptyList());
        when(movieService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/screening-form"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/screening_form"))
                .andExpect(model().attributeExists("screening", "halls", "movies"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testAddScreening() throws Exception {
        mockMvc.perform(post("/screenings/add")
                        .param("price", "20.00")
                        .param("startTime", LocalDateTime.now().toString())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(screeningService, times(1)).save(any(Screening.class));
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void testShowReserveForm() throws Exception {
        MovieDto movie = new MovieDto();
        movie.setTitle("Test Movie");

        HallDto hall = new HallDto();
        hall.setName("Sala 1");

        ScreeningDto screening = new ScreeningDto();
        screening.setId(1L);
        screening.setMovie(movie);
        screening.setStartTime(LocalDateTime.now());
        screening.setPrice(new BigDecimal("25.00").doubleValue());
        screening.setHall(hall);

        when(screeningService.findById(1L)).thenReturn(screening);
        when(screeningService.getAvailableSeatsForScreening(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/screenings/reserve/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("user/reserve"))
                .andExpect(model().attributeExists("ticket", "availableSeats"));
    }
}
