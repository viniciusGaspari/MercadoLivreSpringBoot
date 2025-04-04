package com.github.viniciusgaspari.mercadolivre.common;

import com.github.viniciusgaspari.mercadolivre.Exceptions.ErroCampo;
import com.github.viniciusgaspari.mercadolivre.Exceptions.MensagemErro;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalDefaultHandlerExceptionResolver {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemErro defaultHandlerExceptionResolver(MethodArgumentNotValidException e){

        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErro = fieldErrors
                .stream()
                .map(erro -> new ErroCampo(erro.getField(), erro.getDefaultMessage()))
        .toList();

        return new MensagemErro(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                listaErro
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MensagemErro handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        String errorMessage = String.format("Formato do ID errado");
        return new MensagemErro(
                HttpStatus.BAD_REQUEST.value(),
                "Parametro não condiz",
                List.of(new ErroCampo(e.getName(), errorMessage))
        );
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MensagemErro invalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e){
        String errorMensagem = String.format("Os campos não podem ser nulos");

        return new MensagemErro(
                HttpStatus.BAD_REQUEST.value(),
                "Campo inválido",
                List.of(new ErroCampo(e.getMessage(), errorMensagem)
        ));
    }


}
