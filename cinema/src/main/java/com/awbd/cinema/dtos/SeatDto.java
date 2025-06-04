package com.awbd.cinema.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatDto {
    private Long id;
    private int row_num;
    private int number;
}
