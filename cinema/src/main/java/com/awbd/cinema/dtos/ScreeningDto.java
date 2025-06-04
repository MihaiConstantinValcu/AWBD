package com.awbd.cinema.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ScreeningDto {
    private Long id;
    private LocalDateTime startTime;
    private double price;
    private MovieDto movie;
    private HallDto hall;
    private List<TicketDto> tickets;
}
