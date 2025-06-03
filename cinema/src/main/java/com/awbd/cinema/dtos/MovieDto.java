package com.awbd.cinema.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDto {
    private Long id;
    private String title;
    private String description;
    private int duration;
    private double rating;
}
