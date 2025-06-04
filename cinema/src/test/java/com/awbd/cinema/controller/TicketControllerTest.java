package com.awbd.cinema.controller;

import com.awbd.cinema.domain.Ticket;
import com.awbd.cinema.dtos.*;
import com.awbd.cinema.services.MovieService;
import com.awbd.cinema.services.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Profile("postgres")
public class TicketControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TicketService ticketService;

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void testAddTicket() throws Exception {
        mockMvc.perform(post("/tickets")
                        .param("id", "1")
                        .param("screening.id", "1")
                        .param("seat.id", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/my-tickets"));

        verify(ticketService, times(1)).save(any(Ticket.class), eq("user"));
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void testShowTicketList() throws Exception {
        MovieDto movie = new MovieDto();
        movie.setTitle("Test Movie");

        HallDto hall = new HallDto();
        hall.setName("Sala 1");

        ScreeningDto screening = new ScreeningDto();
        screening.setMovie(movie);
        screening.setHall(hall);
        screening.setStartTime(LocalDateTime.now());

        SeatDto seat = new SeatDto();
        seat.setNumber(5);
        seat.setRow_num(2);

        TicketDto ticket = new TicketDto();
        ticket.setId(1L);
        ticket.setScreening(screening);
        ticket.setSeat(seat);

        Page<TicketDto> page = new PageImpl<>(Collections.singletonList(ticket));

        when(ticketService.findByUsername(eq("user"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/my-tickets")
                        .param("page", "0")
                        .param("sortBy", "screening.startTime")
                        .param("dir", "asc"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/ticket_list"))
                .andExpect(model().attributeExists("tickets", "currentPage", "sortBy", "dir", "nextDir"));
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void testCancelTicket() throws Exception {
        mockMvc.perform(post("/tickets/cancel/{id}", 1L).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/my-tickets"));

        verify(ticketService, times(1)).delete(1L);
    }
}
