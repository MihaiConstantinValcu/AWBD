package com.awbd.ticketapi.services;

import com.awbd.ticketapi.domain.Ticket;
import com.awbd.ticketapi.dtos.TicketDto;
import com.awbd.ticketapi.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;

    public void save(TicketDto ticket, Long userId) {
        Ticket newTicket = modelMapper.map(ticket, Ticket.class);
        newTicket.setUserId(userId);
        ticketRepository.save(newTicket);
    }

    public Page<TicketDto> findByUsername(Long userId, Pageable pageable) {
        return ticketRepository.findAllByUserId(userId, pageable)
                .map(ticket -> modelMapper.map(ticket, TicketDto.class));
    }

    public List<TicketDto> findByUsername(Long userId) {
        return ticketRepository.findAllByUserId(userId).stream()
                .map(ticket -> modelMapper.map(ticket, TicketDto.class))
                .toList();
    }

    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }

    public List<Long> findReservedSeatsIds(Long screeningId) {
        return ticketRepository.findAllByScreeningId(screeningId).stream()
                .map(Ticket::getSeatId)
                .toList();
    }
}
