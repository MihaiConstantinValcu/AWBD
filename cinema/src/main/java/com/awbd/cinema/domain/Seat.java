package com.awbd.cinema.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int row;
    private int number;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;
}