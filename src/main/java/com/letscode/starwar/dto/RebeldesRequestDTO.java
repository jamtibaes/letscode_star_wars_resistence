package com.letscode.starwar.dto;

import com.letscode.starwar.entity.Genero;
import com.letscode.starwar.entity.Inventario;
import com.letscode.starwar.entity.Localizacao;
import com.letscode.starwar.entity.Rebelde;
import lombok.Getter;

@Getter
public class RebeldesRequestDTO {

    private Long id;
    private String nome;
    private int idade;
    private Genero genero;
    private Inventario inventario;
    private Localizacao localizacao;

    public RebeldesRequestDTO() {
    }

    public RebeldesRequestDTO(Rebelde rebelde) {
        this.nome = rebelde.getNome();
        this.idade = rebelde.getIdade();
        this.genero = rebelde.getGenero();
        this.inventario = rebelde.getInventario();
        this.localizacao = rebelde.getLocalizacao();
    }
}
