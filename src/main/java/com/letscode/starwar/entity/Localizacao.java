package com.letscode.starwar.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Localizacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeGalaxia;
    private Double latitude;
    private Double longitude;

}
