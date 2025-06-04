package com.awbd.cinema.service;

import com.awbd.cinema.domain.Ticket;
import com.awbd.cinema.domain.security.User;
import com.awbd.cinema.dtos.TicketDto;
import com.awbd.cinema.exceptions.ResourceNotFoundException;
import com.awbd.cinema.repositories.TicketRepository;
import com.awbd.cinema.repositories.security.UserRepository;
import com.awbd.cinema.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    TicketRepository ticketRepository;
    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    TicketService ticketService;

    User user;
    Ticket ticket;
    TicketDto ticketDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("user");

        ticket = new Ticket();
        ticket.setId(1L);

        ticketDto = new TicketDto();
        ticketDto.setId(1L);
    }

    @Test
    void testSave_withValidUser() {
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        ticketService.save(ticket, "user");

        assertEquals(user, ticket.getUser());
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void testSave_userNotFound() {
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ticketService.save(ticket, "user"));

        verify(ticketRepository, never()).save(any());
    }

    @Test
    void testFindByUsername_withPageable() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Ticket> ticketPage = new PageImpl<>(List.of(ticket));

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(ticketRepository.findAllByUserId(1L, pageable)).thenReturn(ticketPage);
        when(modelMapper.map(ticket, TicketDto.class)).thenReturn(ticketDto);

        Page<TicketDto> result = ticketService.findByUsername("user", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(ticketDto.getId(), result.getContent().get(0).getId());
    }

    @Test
    void testFindByUsername_withPageable_userNotFound() {
        Pageable pageable = PageRequest.of(0, 5);
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> ticketService.findByUsername("user", pageable));
    }

    @Test
    void testFindByUsername_listVersion() {
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(ticketRepository.findAllByUserId(1L)).thenReturn(List.of(ticket));
        when(modelMapper.map(ticket, TicketDto.class)).thenReturn(ticketDto);

        List<TicketDto> result = ticketService.findByUsername("user");

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testFindByUsername_list_userNotFound() {
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ticketService.findByUsername("user"));
    }

    @Test
    void testDelete() {
        ticketService.delete(1L);
        verify(ticketRepository, times(1)).deleteById(1L);
    }
}
