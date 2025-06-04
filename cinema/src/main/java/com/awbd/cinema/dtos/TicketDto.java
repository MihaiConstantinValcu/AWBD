package com.awbd.cinema.dtos;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketDto {
    private Long id;
    private ScreeningDto screening;
    private SeatDto seat;
}
