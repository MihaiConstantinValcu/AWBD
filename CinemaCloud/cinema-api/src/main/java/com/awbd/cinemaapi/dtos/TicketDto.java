package com.awbd.cinemaapi.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketDto {
    private Long id;
    @NotNull
    private Long screeningId;
    @NotNull
    private Long seatId;
    private ScreeningDto screening;
    private SeatDto seat;
}
