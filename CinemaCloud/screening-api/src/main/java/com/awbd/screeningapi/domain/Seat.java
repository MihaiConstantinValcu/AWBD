package com.awbd.screeningapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int row_num;
    private int number;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;
}