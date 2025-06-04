package com.awbd.screeningapi.dtos;

import com.awbd.screeningapi.domain.enums.Rating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDto {
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private int duration;
    @NotNull
    private Rating rating;
    private GenreDto genre;
}
