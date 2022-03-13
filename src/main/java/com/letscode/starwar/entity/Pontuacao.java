package com.letscode.starwar.entity;

public enum Pontuacao {
    ARMA(4),
    MUNICAO(3),
    AGUA(2),
    COMIDA(1);

    public Integer valor;
    Pontuacao(Integer valor){
        this.valor = valor;
    }
}
