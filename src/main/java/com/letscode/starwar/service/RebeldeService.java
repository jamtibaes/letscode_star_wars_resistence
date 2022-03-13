package com.letscode.starwar.service;

import com.letscode.starwar.dto.RebeldesRequestDTO;
import com.letscode.starwar.entity.Inventario;
import com.letscode.starwar.entity.Localizacao;
import com.letscode.starwar.entity.Rebelde;
import com.letscode.starwar.repository.InventarioRepository;
import com.letscode.starwar.repository.LocalizacaoRepository;
import com.letscode.starwar.repository.RebeldeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RebeldeService {

    @Autowired
    private RebeldeRepository rebeldeRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    public List<Rebelde> getRebeldes() {
        List<Rebelde> listaRebeldes = rebeldeRepository.findAll().stream()
                .filter(item -> item.getReporteTraicao() <= 3)
                .collect(Collectors.toList());
        return listaRebeldes;
    }

    public List<Rebelde> getTraidores() {
        List<Rebelde> listaRebeldes = rebeldeRepository.findAll().stream()
                .filter(item -> item.getReporteTraicao() > 3)
                .collect(Collectors.toList());
        return listaRebeldes;
    }

    public Rebelde findById(Long id) {
        Rebelde rebelde = rebeldeRepository.findById(id).get();
        return rebelde;
    }

    public Rebelde save(RebeldesRequestDTO dto) {
        Inventario inventario = instanciarInventario(dto);
        inventarioRepository.save(inventario);
        Localizacao localizacao = instanciarLocalizacao(dto);
        localizacaoRepository.save(localizacao);
        Rebelde rebelde = instanciarRebelde(dto, inventario, localizacao);
        rebeldeRepository.save(rebelde);
        return rebelde;
    }

    private Rebelde instanciarRebelde(RebeldesRequestDTO dto, Inventario inventario, Localizacao localizacao) {
        Rebelde rebelde = Rebelde.builder()
                .nome(dto.getNome())
                .genero(dto.getGenero())
                .idade(dto.getIdade())
                .inventario(inventario)
                .localizacao(localizacao)
                .build();
        return rebelde;
    }

    private Localizacao instanciarLocalizacao(RebeldesRequestDTO dto) {
        Localizacao localizacao = Localizacao.builder()
                .latitude(dto.getLocalizacao().getLatitude())
                .longitude(dto.getLocalizacao().getLongitude())
                .nomeGalaxia(dto.getLocalizacao().getNomeGalaxia())
                .build();
        return localizacao;
    }

    private Inventario instanciarInventario(RebeldesRequestDTO dto) {
        Inventario inventario = Inventario.builder()
                .agua(dto.getInventario().getAgua())
                .comida(dto.getInventario().getComida())
                .arma(dto.getInventario().getArma())
                .municao(dto.getInventario().getMunicao())
                .build();
        return inventario;
    }

}
