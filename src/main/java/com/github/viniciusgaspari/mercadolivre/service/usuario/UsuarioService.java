package com.github.viniciusgaspari.mercadolivre.service.usuario;

import com.github.viniciusgaspari.mercadolivre.model.usuario.UsuarioEntity;
import com.github.viniciusgaspari.mercadolivre.validator.usuario.UsuarioValidator;
import com.github.viniciusgaspari.mercadolivre.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioValidator validator;

    public com.github.viniciusgaspari.mercadolivre.model.usuario.UsuarioEntity salvar(com.github.viniciusgaspari.mercadolivre.model.usuario.UsuarioEntity entity){
        validator.salvarUsuarioExistente(entity);
        return repository.save(entity);
    }

    public Optional<List<UsuarioEntity>> buscarUsuarioPorIdOuNome(UUID id, String nome){
        return validator.buscarUsuarioPorIdOuNome(id, nome);
    }

    public Optional<UsuarioEntity> buscar(UUID id){
        return repository.findById(id);
    }

    public Optional<UsuarioEntity> deletarComFiltro(UUID id, String nome, String nacionalidade, LocalDate dataNascimento){
         return validator.deletarComFiltro(id, nome, nacionalidade, dataNascimento);
    }

    public void deletarUsuario(UsuarioEntity entity){
        repository.delete(entity);
    }

    public Optional<UsuarioEntity> buscarPorId(UUID id){
        validator.buscarUsuarioPorId(id);
        return repository.findById(id);
    }

}
