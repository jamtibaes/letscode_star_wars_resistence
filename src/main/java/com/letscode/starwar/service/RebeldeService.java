package com.letscode.starwar.service;

import com.letscode.starwar.dto.MensagemResponseDTO;
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
                .reporteTraicao(0)
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

    public MensagemResponseDTO updateTraidor(Long id) {
        Rebelde rebelde = rebeldeRepository.getById(id);
        rebelde.setReporteTraicao(rebelde.getReporteTraicao() + 1);
        rebeldeRepository.save(rebelde);
        return MensagemResponseDTO.builder().message("Delatado rebelde ID: " + rebelde.getId()).build();
    }

    public MensagemResponseDTO updateLocalizacao(Long id, Localizacao loc) {
        Localizacao localizacaoEntity = localizacaoRepository.getById(id);
        localizacaoEntity.setNomeGalaxia(loc.getNomeGalaxia());
        localizacaoEntity.setLongitude(loc.getLongitude());
        localizacaoEntity.setLatitude(loc.getLatitude());
        localizacaoRepository.save(localizacaoEntity);
        return MensagemResponseDTO.builder()
                    .message("Atualizado localizacao ID")
                    .build();
    }

    public MensagemResponseDTO getRelatorioRebeldes() {
        return MensagemResponseDTO.builder()
                .message("Temos "
                        + rebeldeRepository
                            .findAll().stream()
                            .filter(item -> item.getReporteTraicao() <= 3)
                            .count()
                        + " rebelde(s) no total de "
                        + rebeldeRepository.count()
                        + " cadastrados na API.")
                .build();
    }

    public MensagemResponseDTO getRelatorioTraidores() {
        return MensagemResponseDTO.builder()
                .message("Temos "
                        + rebeldeRepository
                        .findAll().stream()
                        .filter(item -> item.getReporteTraicao() > 3)
                        .count()
                        + " traidor(es) no total de "
                        + rebeldeRepository.count()
                        + " cadastrados na API.")
                .build();
    }
}
