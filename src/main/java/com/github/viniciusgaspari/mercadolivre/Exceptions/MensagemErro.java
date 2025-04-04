package com.github.viniciusgaspari.mercadolivre.Exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public record MensagemErro(

        int status,
        String mensagem,
        List<ErroCampo> erros

) {

//    public MensagemErro handlerException(String mensagem){
//        return new MensagemErro(HttpStatus.UNPROCESSABLE_ENTITY.value(), mensagem, List.of());
//    }

    public static MensagemErro conflito(String mensagem){
        return new MensagemErro(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }

    public static MensagemErro naoEncontrado(String mensagem){
        return new MensagemErro(HttpStatus.NOT_FOUND.value(), mensagem, List.of());
    }

}
