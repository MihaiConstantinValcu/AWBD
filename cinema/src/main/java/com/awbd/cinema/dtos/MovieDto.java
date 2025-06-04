package com.awbd.cinema.dtos;

import com.awbd.cinema.domain.enums.Rating;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDto {
    private Long id;
    private String title;
    private String description;
    private int duration;
    private Rating rating;
    private GenreDto genre;
}
