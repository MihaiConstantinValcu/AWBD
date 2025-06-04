package com.awbd.cinema.services;

import com.awbd.cinema.domain.Ticket;
import com.awbd.cinema.domain.security.User;
import com.awbd.cinema.dtos.TicketDto;
import com.awbd.cinema.exceptions.ResourceNotFoundException;
import com.awbd.cinema.repositories.TicketRepository;
import com.awbd.cinema.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;

    public void save(Ticket ticket, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
        ticket.setUser(user);
        ticketRepository.save(ticket);
    }

    public Page<TicketDto> findByUsername(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
        return ticketRepository.findAllByUserId(user.getId(), pageable)
                .map(ticket -> modelMapper.map(ticket, TicketDto.class));
    }

    public List<TicketDto> findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
        return ticketRepository.findAllByUserId(user.getId()).stream()
                .map(ticket -> modelMapper.map(ticket, TicketDto.class))
                .toList();
    }

    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }
}
