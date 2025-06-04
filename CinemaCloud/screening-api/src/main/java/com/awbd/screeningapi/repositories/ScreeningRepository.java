package com.awbd.screeningapi.repositories;

import com.awbd.screeningapi.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> findAllByMovieId(Long id);
}
