package com.awbd.screeningapi.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ScreeningDto {
    private Long id;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private double price;
    @NotNull
    private MovieDto movie;
    @NotNull
    private HallDto hall;
}
