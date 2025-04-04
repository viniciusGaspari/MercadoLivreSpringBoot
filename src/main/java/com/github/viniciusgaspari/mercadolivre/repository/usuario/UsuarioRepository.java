package com.github.viniciusgaspari.mercadolivre.repository.usuario;

import com.github.viniciusgaspari.mercadolivre.model.usuario.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {


    boolean existsByNome(String entity);

    Optional<UsuarioEntity> findByIdOrNomeOrNacionalidadeOrDataNascimento(UUID id, String nome, String nacionalidade, LocalDate dataNascimento);

    boolean existsById(UUID id);

    boolean existsByIdOrNome(UUID id, String nome);

    Optional<List<UsuarioEntity>> findByNomeOrId(String nome, UUID id);

}
