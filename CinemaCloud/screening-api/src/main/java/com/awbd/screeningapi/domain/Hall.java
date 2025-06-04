package com.awbd.screeningapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<Seat> seats;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<Screening> screenings;
}