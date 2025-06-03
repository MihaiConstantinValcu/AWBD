package com.awbd.cinema.controllers;

import com.awbd.cinema.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MovieService movieService;

    @RequestMapping({"","/","/home"})
    public ModelAndView getHome(){
        ModelAndView mav = new ModelAndView("main");
        mav.addObject("movies", movieService.findAll());

        return mav;
    }

    @GetMapping("/login")
    public String showLogInForm(){ return "login"; }

    @GetMapping("/access_denied")
    public String accessDeniedPage(){ return "accessDenied"; }
}
