package com.letscode.starwar.service;

import com.letscode.starwar.dto.MensagemResponseDTO;
import com.letscode.starwar.dto.RebeldesRequestDTO;
import com.letscode.starwar.dto.TrocasRequestDTO;
import com.letscode.starwar.entity.Inventario;
import com.letscode.starwar.entity.Localizacao;
import com.letscode.starwar.entity.Pontuacao;
import com.letscode.starwar.entity.Rebelde;
import com.letscode.starwar.exception.RebeldesNaoEncontradoExcessao;
import com.letscode.starwar.exception.TraidorNaoEncontradoExcessao;
import com.letscode.starwar.repository.InventarioRepository;
import com.letscode.starwar.repository.LocalizacaoRepository;
import com.letscode.starwar.repository.RebeldeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
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

    public Rebelde findById(Long id) throws RebeldesNaoEncontradoExcessao {
        Optional<Rebelde> rebelde = rebeldeRepository.findById(id);
        if(rebelde.isEmpty()){
            throw new RebeldesNaoEncontradoExcessao(id);
        }
        return rebelde.get();
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

    public MensagemResponseDTO updateTraidor(Long id) throws TraidorNaoEncontradoExcessao {
        Optional<Rebelde> traidor  = rebeldeRepository.findById(id);
        if (traidor.isEmpty()) {
            throw new TraidorNaoEncontradoExcessao(id);
        }
        traidor.get().setReporteTraicao(traidor.get().getReporteTraicao() + 1);
        rebeldeRepository.save(traidor.get());
        return MensagemResponseDTO.builder().message("Delatado rebelde ID: " + traidor.get().getId()).build();
    }

    public MensagemResponseDTO updateLocalizacao(Long id, Localizacao loc) throws RebeldesNaoEncontradoExcessao {
        Optional<Rebelde> rebelde = rebeldeRepository.findById(id);
        if(rebelde.isEmpty()){
            throw new RebeldesNaoEncontradoExcessao(id);
        }
        if(rebelde.get().getReporteTraicao() <= 3){
            Localizacao localizacaoEntity = localizacaoRepository.getById(id);
            localizacaoEntity.setNomeGalaxia(loc.getNomeGalaxia());
            localizacaoEntity.setLongitude(loc.getLongitude());
            localizacaoEntity.setLatitude(loc.getLatitude());
            localizacaoRepository.save(localizacaoEntity);
            return MensagemResponseDTO.builder()
                    .message("Atualizado localizacao ID")
                    .build();
        } else {
            return MensagemResponseDTO.builder().message("Traidor não pode ter localização atualizada!").build();
        }
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

    public MensagemResponseDTO getRelatorioMediaRecursos(){
        Inventario inventario = new Inventario();
        inventario.setAgua(0);
        inventario.setArma(0);
        inventario.setComida(0);
        inventario.setMunicao(0);
        Long contador = rebeldeRepository.findAll().stream().filter(item -> item.getReporteTraicao() <= 3).count();
        rebeldeRepository.findAll().stream().filter(item -> item.getReporteTraicao() <= 3).forEach(item -> {
            inventario.setAgua(inventario.getAgua() + item.getInventario().getAgua());
            inventario.setArma(inventario.getArma() + item.getInventario().getArma());
            inventario.setComida(inventario.getComida() + item.getInventario().getComida());
            inventario.setMunicao(inventario.getMunicao() + item.getInventario().getMunicao());
        });

        try{
            return MensagemResponseDTO.builder()
                    .message(
                            "Média por rebelde(Água: " + (long)inventario.getAgua()/contador +
                                    ", Arma: " + (long)inventario.getArma()/contador +
                                    ", Municao: " + (long)inventario.getMunicao()/contador +
                                    ", Comida: " + (long)inventario.getComida()/contador + ")")
                    .build();
        } catch (ArithmeticException e) {
            return MensagemResponseDTO.builder().message("Sem dados para apresentar").build();
        }

    }

    public MensagemResponseDTO getRelatorioPontosPerdidosTraidores() {
        AtomicReference<Long> pontosPerdidos = new AtomicReference<>(0L);

        rebeldeRepository.findAll().stream().filter(item -> item.getReporteTraicao() > 3).forEach(item -> {
            pontosPerdidos.updateAndGet(v -> v + item.getInventario().getAgua() * Pontuacao.AGUA.valor);
            pontosPerdidos.updateAndGet(v -> v + item.getInventario().getArma() * Pontuacao.ARMA.valor);
            pontosPerdidos.updateAndGet(v -> v + item.getInventario().getComida() * Pontuacao.COMIDA.valor);
            pontosPerdidos.updateAndGet(v -> v + item.getInventario().getMunicao() * Pontuacao.MUNICAO.valor);
        });

        return MensagemResponseDTO.builder()
                .message("Foram perdidos no total de " + pontosPerdidos + " pontos por traição")
                .build();
    }

    public MensagemResponseDTO negociarItens(TrocasRequestDTO trocasRequestDTO) {

        Rebelde rebeldeComprador = rebeldeRepository.getById(trocasRequestDTO.getIdComprador());
        Rebelde rebeldeVendedor = rebeldeRepository.getById(trocasRequestDTO.getIdVendedor());

        if (rebeldeComprador.getReporteTraicao() <= 3 && rebeldeVendedor.getReporteTraicao() <= 3) {

            /* Checar se o comprador e vendedor possuem itens no inventario */
            Inventario inventarioComprador = inventarioRepository.getById(trocasRequestDTO.getIdComprador());
            Inventario inventarioVendedor = inventarioRepository.getById(trocasRequestDTO.getIdVendedor());

            if (inventarioComprador.getComida() < trocasRequestDTO.getComidaComprador() ||
                    inventarioComprador.getArma() < trocasRequestDTO.getArmaComprador() ||
                    inventarioComprador.getMunicao() < trocasRequestDTO.getMunicaoComprador() ||
                    inventarioComprador.getAgua() < trocasRequestDTO.getAguaComprador()) {
                return MensagemResponseDTO.builder().message("Comprador não possue os itens para troca").build();
            }

            if (inventarioVendedor.getComida() < trocasRequestDTO.getComidaVendedor() ||
                    inventarioVendedor.getArma() < trocasRequestDTO.getArmaVendedor() ||
                    inventarioVendedor.getMunicao() < trocasRequestDTO.getMunicaoVendedor() ||
                    inventarioVendedor.getAgua() < trocasRequestDTO.getAguaVendedor()) {
                return MensagemResponseDTO.builder().message("Vendedor não possue os itens para troca").build();
            }

            /* Verificar a soma dos itens são iguais*/
            if ((Pontuacao.ARMA.valor * trocasRequestDTO.getArmaComprador() +
                 Pontuacao.MUNICAO.valor * trocasRequestDTO.getMunicaoComprador() +
                 Pontuacao.COMIDA.valor * trocasRequestDTO.getComidaComprador() +
                 Pontuacao.AGUA.valor * trocasRequestDTO.getAguaComprador()) == (
                    Pontuacao.ARMA.valor * trocasRequestDTO.getArmaVendedor() +
                    Pontuacao.MUNICAO.valor * trocasRequestDTO.getMunicaoVendedor() +
                    Pontuacao.COMIDA.valor * trocasRequestDTO.getComidaVendedor() +
                    Pontuacao.AGUA.valor * trocasRequestDTO.getAguaVendedor()
            )) {
                inventarioComprador.setAgua(inventarioComprador.getAgua() + trocasRequestDTO.getAguaVendedor());
                inventarioComprador.setArma(inventarioComprador.getArma() + trocasRequestDTO.getArmaVendedor());
                inventarioComprador.setComida(inventarioComprador.getComida() + trocasRequestDTO.getComidaVendedor());
                inventarioComprador.setMunicao(inventarioComprador.getMunicao() + trocasRequestDTO.getMunicaoVendedor());

                inventarioComprador.setAgua(inventarioComprador.getAgua() - trocasRequestDTO.getAguaComprador());
                inventarioComprador.setArma(inventarioComprador.getArma() - trocasRequestDTO.getArmaComprador());
                inventarioComprador.setComida(inventarioComprador.getComida() - trocasRequestDTO.getComidaComprador());
                inventarioComprador.setMunicao(inventarioComprador.getMunicao() - trocasRequestDTO.getMunicaoComprador());

                inventarioVendedor.setAgua(inventarioVendedor.getAgua() + trocasRequestDTO.getAguaComprador());
                inventarioVendedor.setArma(inventarioVendedor.getArma() + trocasRequestDTO.getArmaComprador());
                inventarioVendedor.setComida(inventarioVendedor.getComida() + trocasRequestDTO.getComidaComprador());
                inventarioVendedor.setMunicao(inventarioVendedor.getMunicao() + trocasRequestDTO.getMunicaoComprador());

                inventarioVendedor.setAgua(inventarioVendedor.getAgua() - trocasRequestDTO.getAguaVendedor());
                inventarioVendedor.setArma(inventarioVendedor.getArma() - trocasRequestDTO.getArmaVendedor());
                inventarioVendedor.setComida(inventarioVendedor.getComida() - trocasRequestDTO.getComidaVendedor());
                inventarioVendedor.setMunicao(inventarioVendedor.getMunicao() - trocasRequestDTO.getMunicaoVendedor());

                inventarioRepository.save(inventarioComprador);
                inventarioRepository.save(inventarioVendedor);
                return MensagemResponseDTO.builder().message("Transação realizada com sucesso").build();
            } else {
                return MensagemResponseDTO.builder().message("Valores dos itens diferentes").build();
            }

        } else {
            return MensagemResponseDTO.builder().message("Trocas não podem ser realizado entre rebeldes e traidores").build();
        }
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
}
