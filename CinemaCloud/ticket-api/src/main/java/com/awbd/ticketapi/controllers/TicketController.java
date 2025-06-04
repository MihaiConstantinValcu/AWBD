package com.awbd.ticketapi.controllers;

import com.awbd.ticketapi.dtos.TicketDto;
import com.awbd.ticketapi.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public void createTicket(@RequestBody TicketDto ticket, @RequestParam Long userId) {
        ticketService.save(ticket, userId);
    }

    @GetMapping
    public List<TicketDto> getTicketsByUserId(@RequestParam Long userId) {
        return ticketService.findByUsername(userId);
    }

    @GetMapping("/paged")
    public Page<TicketDto> getTicketsByUserIdPaged(@RequestParam Long userId, Pageable pageable) {
        return ticketService.findByUsername(userId, pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketService.delete(id);
    }

    @GetMapping("/reservedSeats")
    public List<Long> getReservedSeats(@RequestParam Long screeningId) {
        return ticketService.findReservedSeatsIds(screeningId);
    }
}