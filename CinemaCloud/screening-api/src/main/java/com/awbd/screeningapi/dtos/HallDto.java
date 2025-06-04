package com.awbd.screeningapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HallDto {
    private Long id;
    private String name;
    private List<SeatDto> seats;
}
