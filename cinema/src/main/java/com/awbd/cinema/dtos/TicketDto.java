package com.awbd.cinema.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketDto {
    private Long id;
    @NotNull
    private ScreeningDto screening;
    @NotNull
    private SeatDto seat;
}
