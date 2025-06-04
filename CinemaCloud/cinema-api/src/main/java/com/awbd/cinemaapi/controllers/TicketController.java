package com.awbd.cinemaapi.controllers;

import com.awbd.cinemaapi.dtos.TicketDto;
import com.awbd.cinemaapi.services.ScreeningService;
import com.awbd.cinemaapi.services.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final ScreeningService screeningService;
    private final ModelMapper modelMapper;

    @PostMapping("/tickets")
    public String addTicket(@Valid @ModelAttribute("ticket") TicketDto ticket, Principal principal) {
        ticketService.save(ticket, principal.getName());
        return "redirect:/my-tickets";
    }

    @RequestMapping("/my-tickets")
    public String showTicketList(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "id") String sortBy,
                                 @RequestParam(defaultValue = "asc") String dir,
                                 Model model,
                                 Principal principal) {

        Sort sort = dir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<TicketDto> tickets = ticketService.findByUsername(principal.getName(), pageable)
                .map(ticket -> {
                    ticket.setScreening(screeningService.findById(ticket.getScreeningId()));
                    ticket.setSeat(screeningService.findSeatById(ticket.getSeatId()));
                    return ticket;
                });

        model.addAttribute("tickets", tickets);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("dir", dir);
        model.addAttribute("nextDir", dir.equals("asc") ? "desc" : "asc");

        return "user/ticket_list";
    }


    @PostMapping("/tickets/cancel/{id}")
    public String cancelTicket(@PathVariable Long id) {
        ticketService.delete(id);
        return "redirect:/my-tickets";
    }
}
