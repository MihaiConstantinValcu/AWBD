package com.awbd.cinema.services;

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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final HallRepository hallRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;

    public List<HallDto> findAllHalls() {
        return hallRepository.findAll().stream()
                .map(hall -> modelMapper.map(hall, HallDto.class))
                .toList();
    }

    public void save(Screening screening) {
        screeningRepository.save(screening);
    }

    public List<ScreeningDto> findAllForMovie(Long id) {
        return screeningRepository.findAllByMovieId(id).stream()
                .map(screening -> modelMapper.map(screening, ScreeningDto.class))
                .toList();
    }

    public ScreeningDto findById(Long id) {
        return screeningRepository.findById(id)
                .map(screening -> modelMapper.map(screening, ScreeningDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Screening not found with id " + id));
    }

    public List<SeatDto> getAvailableSeatsForScreening(Long id) {
        Screening screening = screeningRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Screening not found with id " + id)
        );

        Hall hall = hallRepository.findById(screening.getHall().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Hall not found with id " + id));

        List<Seat> allSeats = seatRepository.findAllByHallId(hall.getId());

        List<Seat> reservedSeats = ticketRepository.findAllByScreeningId(id)
                .stream()
                .map(Ticket::getSeat)
                .toList();

        List<Seat> availableSeats = allSeats.stream()
                .filter(seat -> !reservedSeats.contains(seat))
                .toList();

        return availableSeats.stream()
                .map(seat -> modelMapper.map(seat, SeatDto.class))
                .toList();
    }
}
