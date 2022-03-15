package com.letscode.starwar.controller;

import com.letscode.starwar.dto.MensagemResponseDTO;
import com.letscode.starwar.dto.RebeldesRequestDTO;
import com.letscode.starwar.entity.Localizacao;
import com.letscode.starwar.entity.Rebelde;
import com.letscode.starwar.service.RebeldeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.URI;
import java.util.List;

@EnableSwagger2
@RestController
@RequestMapping(value = "/v1")
public class StarWarsController {

    @Autowired
    private RebeldeService service;

    @GetMapping("/rebeldes")
    public ResponseEntity<List<Rebelde>> getRebeldes() {
        List<Rebelde> listaRebeldes = service.getRebeldes();
        return ResponseEntity.ok(listaRebeldes);
    }

    @GetMapping("/traidores")
    public ResponseEntity<List<Rebelde>> getTraidores() {
        List<Rebelde> listaRebeldes = service.getTraidores();
        return ResponseEntity.ok(listaRebeldes);
    }

    @GetMapping("/rebeldes/{id}")
    public ResponseEntity<Rebelde> getRebeldeId(@PathVariable Long id) {
        Rebelde rebelde = service.findById(id);
        return ResponseEntity.ok(rebelde);
    }

    @PostMapping("/rebeldes")
    public ResponseEntity<Rebelde> save(@RequestBody RebeldesRequestDTO rebelde, UriComponentsBuilder uriBuilder) {
        Rebelde rebeldeEntity = service.save(rebelde);
        URI uri = uriBuilder.path("rebeldes/{id}").buildAndExpand(rebeldeEntity.getId()).toUri();
        return ResponseEntity.created(uri).body(rebeldeEntity);
    }

    @PostMapping("/traidores/{id}")
    public MensagemResponseDTO postTraidor(@PathVariable Long id) {
        return service.updateTraidor(id);
    }

    @PutMapping("/localizacao/{id}")
    public MensagemResponseDTO updateLocalizacao(@PathVariable Long id, @RequestBody Localizacao loc) {
        return service.updateLocalizacao(id, loc);
    }

    @GetMapping("/relatorio/rebeldes")
    public MensagemResponseDTO getRelatorioRebeldes() {
        return service.getRelatorioRebeldes();
    }

    @GetMapping("/relatorio/traidores")
    public MensagemResponseDTO getRelatorioTraidores() {
        return service.getRelatorioTraidores();
    }



}
