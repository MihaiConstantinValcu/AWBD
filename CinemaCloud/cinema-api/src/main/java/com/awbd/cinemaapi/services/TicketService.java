package com.awbd.cinemaapi.services;

import com.awbd.cinemaapi.domain.security.User;
import com.awbd.cinemaapi.dtos.TicketDto;
import com.awbd.cinemaapi.exceptions.ResourceNotFoundException;
import com.awbd.cinemaapi.feigns.TicketFeignClient;
import com.awbd.cinemaapi.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final UserRepository userRepository;
    private final TicketFeignClient ticketFeignClient;

    public void save(TicketDto ticket, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
        ticketFeignClient.createTicket(ticket, user.getId());
    }

    public Page<TicketDto> findByUsername(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));

        return ticketFeignClient.getTicketsByUserIdPaged(user.getId(), pageable);
    }

    public void delete(Long id) {
        ticketFeignClient.deleteTicket(id);
    }

    public List<Long> findReservedSeats(Long screeningId) {
        return ticketFeignClient.getReservedSeats(screeningId);
    }
}
