package com.awbd.cinemaapi.feigns;

import com.awbd.cinemaapi.dtos.TicketDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ticket-api")
public interface TicketFeignClient {

    @PostMapping("/api/tickets")
    void createTicket(@RequestBody TicketDto ticket, @RequestParam("userId") Long userId);

    @GetMapping("/api/tickets")
    List<TicketDto> getTicketsByUserId(@RequestParam("userId") Long userId);

    @GetMapping("/api/tickets/paged")
    Page<TicketDto> getTicketsByUserIdPaged(@RequestParam("userId") Long userId, Pageable pageable);

    @DeleteMapping("/api/tickets/{id}")
    void deleteTicket(@PathVariable Long id);

    @GetMapping("/api/tickets/reservedSeats")
    List<Long> getReservedSeats(@RequestParam Long screeningId);
}