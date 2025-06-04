package com.awbd.cinema.service;

import com.awbd.cinema.domain.Hall;
import com.awbd.cinema.domain.Screening;
import com.awbd.cinema.domain.Seat;
import com.awbd.cinema.domain.Ticket;
import com.awbd.cinema.dtos.HallDto;
import com.awbd.cinema.dtos.ScreeningDto;
import com.awbd.cinema.dtos.SeatDto;
import com.awbd.cinema.exceptions.ResourceNotFoundException;
import com.awbd.cinema.repositories.HallRepository;
import com.awbd.cinema.repositories.ScreeningRepository;
import com.awbd.cinema.repositories.SeatRepository;
import com.awbd.cinema.repositories.TicketRepository;
import com.awbd.cinema.services.ScreeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScreeningServiceTest {

    @Mock
    HallRepository hallRepository;
    @Mock
    ScreeningRepository screeningRepository;
    @Mock
    SeatRepository seatRepository;
    @Mock
    TicketRepository ticketRepository;
    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    ScreeningService screeningService;

    Hall hall;
    HallDto hallDto;
    Screening screening;
    ScreeningDto screeningDto;
    Seat seat;
    SeatDto seatDto;

    @BeforeEach
    void setUp() {
        hall = new Hall();
        hall.setId(1L);

        hallDto = new HallDto();
        hallDto.setId(1L);

        screening = new Screening();
        screening.setId(1L);
        screening.setHall(hall);

        screeningDto = new ScreeningDto();
        screeningDto.setId(1L);

        seat = new Seat();
        seat.setId(1L);
        seat.setHall(hall);

        seatDto = new SeatDto();
        seatDto.setId(1L);
    }

    @Test
    void testFindAllHalls() {
        when(hallRepository.findAll()).thenReturn(List.of(hall));
        when(modelMapper.map(hall, HallDto.class)).thenReturn(hallDto);

        List<HallDto> result = screeningService.findAllHalls();

        assertEquals(1, result.size());
        assertEquals(hallDto.getId(), result.get(0).getId());
    }

    @Test
    void testSave() {
        screeningService.save(screening);
        verify(screeningRepository, times(1)).save(screening);
    }

    @Test
    void testFindAllForMovie() {
        when(screeningRepository.findAllByMovieId(1L)).thenReturn(List.of(screening));
        when(modelMapper.map(screening, ScreeningDto.class)).thenReturn(screeningDto);

        List<ScreeningDto> result = screeningService.findAllForMovie(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testFindById_found() {
        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));
        when(modelMapper.map(screening, ScreeningDto.class)).thenReturn(screeningDto);

        ScreeningDto result = screeningService.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testFindById_notFound() {
        when(screeningRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> screeningService.findById(1L));
    }

    @Test
    void testGetAvailableSeatsForScreening_foundAndAvailable() {
        Ticket ticket = new Ticket();
        ticket.setSeat(seat);

        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));
        when(hallRepository.findById(hall.getId())).thenReturn(Optional.of(hall));
        when(seatRepository.findAllByHallId(hall.getId())).thenReturn(List.of(seat));
        when(ticketRepository.findAllByScreeningId(1L)).thenReturn(List.of(ticket));

        List<SeatDto> result = screeningService.getAvailableSeatsForScreening(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAvailableSeats_someSeatsAvailable() {
        Ticket ticket = new Ticket();
        Seat reservedSeat = new Seat();
        reservedSeat.setId(2L);
        reservedSeat.setHall(hall);
        ticket.setSeat(reservedSeat);

        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));
        when(hallRepository.findById(hall.getId())).thenReturn(Optional.of(hall));
        when(seatRepository.findAllByHallId(hall.getId())).thenReturn(List.of(seat, reservedSeat));
        when(ticketRepository.findAllByScreeningId(1L)).thenReturn(List.of(ticket));
        when(modelMapper.map(seat, SeatDto.class)).thenReturn(seatDto);

        List<SeatDto> result = screeningService.getAvailableSeatsForScreening(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testGetAvailableSeats_screeningNotFound() {
        when(screeningRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> screeningService.getAvailableSeatsForScreening(1L));
    }

    @Test
    void testGetAvailableSeats_hallNotFound() {
        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));
        when(hallRepository.findById(hall.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> screeningService.getAvailableSeatsForScreening(1L));
    }
}
