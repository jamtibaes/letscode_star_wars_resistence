package com.letscode.starwar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TraidorNaoEncontradoExcessao extends Exception{
    public TraidorNaoEncontradoExcessao(Long id) {
        super("Traidor ID" + id + " não foi encontrado");
    }
}
