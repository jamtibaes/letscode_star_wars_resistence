package com.letscode.starwar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/v1")
public class StarWarsController {

    @GetMapping(value = "/rebeldes")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("OK, Google");
    }

}
