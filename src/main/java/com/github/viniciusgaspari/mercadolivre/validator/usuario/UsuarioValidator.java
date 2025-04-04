package com.github.viniciusgaspari.mercadolivre.validator.usuario;

import com.github.viniciusgaspari.mercadolivre.Exceptions.OperacaoNaoPermitida;
import com.github.viniciusgaspari.mercadolivre.model.usuario.UsuarioEntity;
import com.github.viniciusgaspari.mercadolivre.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UsuarioValidator {

    private final UsuarioRepository repository;

    public void salvarUsuarioExistente(UsuarioEntity entity) {

        if (repository.existsByNome(entity.getNome())) {
            throw new OperacaoNaoPermitida("O nome já existe no sistema");
        }


    }

    public Optional<List<UsuarioEntity>> buscarUsuarioPorIdOuNome(UUID id, String nome) {

        if (repository.existsByIdOrNome(id, nome)) {
            return repository.findByNomeOrId(nome, id);
        }

        return Optional.of(repository.findAll());
    }

    public Optional<UsuarioEntity> buscarUsuarioPorId(UUID id){

        if(repository.existsById(id)){
            throw new OperacaoNaoPermitida("Não existe esse usuário");
        }

        return null;
    }

    public Optional<UsuarioEntity> deletarComFiltro(UUID id, String nome, String nacionalidade, LocalDate dataNascimento) {

        return repository.findByIdOrNomeOrNacionalidadeOrDataNascimento(id, nome, nacionalidade, dataNascimento);

    }

}
