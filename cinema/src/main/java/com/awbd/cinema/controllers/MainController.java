package com.awbd.cinema.controllers;

import com.awbd.cinema.dtos.UserDto;
import com.awbd.cinema.services.MovieService;
import com.awbd.cinema.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final MovieService movieService;
    private final UserService userService;

    @RequestMapping({"", "/", "/home"})
    public ModelAndView getHome(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "duration") String sortBy,
                                @RequestParam(defaultValue = "asc") String dir) {

        Sort sort = dir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageable = PageRequest.of(page, 3, sort);

        ModelAndView mav = new ModelAndView("main");
        mav.addObject("movies", movieService.findAll(pageable));
        mav.addObject("currentPage", page);
        mav.addObject("sortBy", sortBy);
        mav.addObject("dir", dir);
        mav.addObject("nextDir", dir.equals("asc") ? "desc" : "asc");

        return mav;
    }


    @GetMapping("/login")
    public String showLogInForm(){ return "login"; }

    @RequestMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDto userDto, RedirectAttributes redirectAttributes) {
        try {
            userService.register(userDto);
            log.info("User registered successfully: {}", userDto.getUsername());
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "true");
            redirectAttributes.addAttribute("message", e.getMessage());
            log.error("Error registering user: {}", userDto.getUsername(), e);
            return "redirect:/register?error";
        }
    }

    @GetMapping("/access_denied")
    public String accessDeniedPage(){ return "accessDenied"; }
}
