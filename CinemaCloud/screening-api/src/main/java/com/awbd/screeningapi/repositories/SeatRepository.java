package com.awbd.screeningapi.repositories;

import com.awbd.screeningapi.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAllByHallId(Long id);
    List<Seat> findAllByIdIn(List<Long> ids);
}
