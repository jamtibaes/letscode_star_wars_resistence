package com.letscode.starwar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RebeldesNaoEncontradoExcessao extends Exception {
    public RebeldesNaoEncontradoExcessao(Long id) {
        super("Rebelde ID" + id + " não foi encontrado");
    }
}
