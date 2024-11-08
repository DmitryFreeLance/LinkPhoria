package ru.linkphoria.linkphoriaproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LinkController {
    @GetMapping("/")
    public String links(){
        return "welcome";
    }
}
