package com.awbd.cinema.repositories;

import com.awbd.cinema.domain.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByScreeningId(Long id);

    List<Ticket> findAllByUserId(Long id);

    Page<Ticket> findAllByUserId(Long id, Pageable pageable);
}
