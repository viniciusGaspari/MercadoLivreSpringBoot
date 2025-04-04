package com.github.viniciusgaspari.mercadolivre.model.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UsuarioDTO(

        @NotBlank(message = "Campo não pode ficar em branco")
        @Size(min = 5, max = 50, message = "campo fora do padrão")
        String nome,

        @NotNull(message = "Campo não pode ficar em branco")
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataNascimento,

        @NotBlank(message = "Campo não pode ficar em branco")
        @Size(min = 5, max = 50, message = "campo fora do tamanho padrão")
        String nacionalidade

        ) {

}
