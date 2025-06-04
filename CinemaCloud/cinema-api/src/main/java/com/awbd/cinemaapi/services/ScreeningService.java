package com.awbd.cinemaapi.services;

import com.awbd.cinemaapi.dtos.HallDto;
import com.awbd.cinemaapi.dtos.ScreeningDto;
import com.awbd.cinemaapi.dtos.SeatDto;
import com.awbd.cinemaapi.exceptions.ResourceNotFoundException;
import com.awbd.cinemaapi.feigns.ScreeningFeignClient;
import com.awbd.cinemaapi.feigns.TicketFeignClient;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ModelMapper modelMapper;
    private final ScreeningFeignClient screeningFeignClient;
    private final TicketService ticketService;
    private final TicketFeignClient ticketFeignClient;

    public List<HallDto> findAllHalls() {
        return screeningFeignClient.getAllHalls();
    }

    public void save(ScreeningDto screening) {
        screeningFeignClient.saveScreening(screening);
    }

    public List<ScreeningDto> findAllForMovie(Long id) {
        return screeningFeignClient.getScreeningsForMovie(id);
    }

    public ScreeningDto findById(Long id) {
        try{
            return screeningFeignClient.getScreeningById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Screening with id " + id + " not found");
        }
    }

    public List<SeatDto> getAvailableSeatsForScreening(Long id) {

        try {
            ScreeningDto screening = screeningFeignClient.getScreeningById(id);
            HallDto hall = screening.getHall();
            List<Long> allSeats = screeningFeignClient.getSeatsByHallId(hall.getId()).stream()
                    .map(SeatDto::getId)
                    .toList();
            List<Long> reservedSeats = ticketService.findReservedSeats(id);

            List<Long> availableSeats = allSeats.stream()
                    .filter(seat -> !reservedSeats.contains(seat))
                    .toList();
            return screeningFeignClient.getSeatsByIdIn(availableSeats);

        } catch (Exception e){
            throw new ResourceNotFoundException("A resource was not found");
        }
    }

    public SeatDto findSeatById(Long seatId) {
        return screeningFeignClient.getSeatById(seatId);
    }
}
