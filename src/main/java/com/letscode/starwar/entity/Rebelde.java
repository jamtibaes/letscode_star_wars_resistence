package com.letscode.starwar.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rebelde{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer idade;

    @Enumerated(value = EnumType.STRING)
    private Genero genero;

    @OneToOne
    private Inventario inventario;

    @OneToOne
    private Localizacao localizacao;

    private Integer reporteTraicao;

}
