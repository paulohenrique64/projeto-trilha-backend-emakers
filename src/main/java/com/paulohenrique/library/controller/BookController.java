package com.paulohenrique.library.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping("/bye")
    public String bye() {
        return "bye endpoint";
    }
}
