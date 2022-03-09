package com.letscode.starwar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class StarWarsController {

    @GetMapping
    public String get() {
        return "OK, Google";
    }

}
