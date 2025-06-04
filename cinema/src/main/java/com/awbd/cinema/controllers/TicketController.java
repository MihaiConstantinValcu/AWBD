package com.awbd.cinema.controllers;

import com.awbd.cinema.domain.Ticket;
import com.awbd.cinema.dtos.TicketDto;
import com.awbd.cinema.services.TicketService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/tickets")
    public String addTicket(@ModelAttribute("ticket") Ticket ticket, Principal principal) {
        ticketService.save(ticket, principal.getName());
        return "redirect:/my-tickets";
    }

    @RequestMapping("/my-tickets")
    public String showTicketList(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "screening.startTime") String sortBy,
                                 @RequestParam(defaultValue = "asc") String dir,
                                 Model model,
                                 Principal principal) {

        Sort sort = dir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<TicketDto> tickets = ticketService.findByUsername(principal.getName(), pageable);

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
