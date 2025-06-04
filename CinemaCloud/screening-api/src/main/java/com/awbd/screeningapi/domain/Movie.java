package com.awbd.screeningapi.domain;

import com.awbd.screeningapi.domain.enums.Rating;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private int duration;
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Screening> screenings;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
