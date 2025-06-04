package com.awbd.cinema.repositories;

import com.awbd.cinema.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    List<Screening> findAllByMovieId(Long movieId);
}
