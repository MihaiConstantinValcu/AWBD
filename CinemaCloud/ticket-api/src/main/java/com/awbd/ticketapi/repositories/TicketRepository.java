package com.awbd.ticketapi.repositories;

import com.awbd.ticketapi.domain.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByScreeningId(Long screeningId);
    List<Ticket> findAllByUserId(Long userId);
    Page<Ticket> findAllByUserId(Long userId, Pageable pageable);
}
