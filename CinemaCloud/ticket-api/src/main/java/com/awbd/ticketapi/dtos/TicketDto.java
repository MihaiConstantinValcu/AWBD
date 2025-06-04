package com.awbd.ticketapi.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketDto {
    private Long id;
    private Long screeningId;
    private Long seatId;
}
