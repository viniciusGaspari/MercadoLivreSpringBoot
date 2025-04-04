package com.github.viniciusgaspari.mercadolivre.controller.usuario;

import com.github.viniciusgaspari.mercadolivre.Exceptions.MensagemErro;
import com.github.viniciusgaspari.mercadolivre.Exceptions.OperacaoNaoPermitida;
import com.github.viniciusgaspari.mercadolivre.model.usuario.UsuarioEntity;
import com.github.viniciusgaspari.mercadolivre.model.usuario.dto.UsuarioDTO;
import com.github.viniciusgaspari.mercadolivre.model.usuario.mapper.UsuarioMapper;
import com.github.viniciusgaspari.mercadolivre.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("usuario")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvarUsuario(@RequestBody @Valid UsuarioDTO dto) {

        try {

            UsuarioEntity entitySalvo = mapper.toEntity(dto);

            service.salvar(entitySalvo);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entitySalvo.getId()).toUri();

            return ResponseEntity.created(location).build();

        } catch (OperacaoNaoPermitida e) {

            var erro = MensagemErro.conflito(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(
            @PathVariable(value = "id", required = true) UUID id
    ) {

        Optional<UsuarioEntity> usuarioEncontrado = service.buscarPorId(id);

        return usuarioEncontrado
                .map(usuarioEntity -> {
                    UsuarioDTO dto = mapper.toDTO(usuarioEntity);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> buscarUsuario(
            @RequestParam(value = "id", required = false) UUID id,
            @RequestParam(value = "nome", required = false) String nome
    ) {

        Optional<List<UsuarioEntity>> usuario = service.buscarUsuarioPorIdOuNome(id, nome);

        return usuario
                .map(
                        usuarioEncontrado -> {
                            List<UsuarioDTO> dto = mapper.toListDTO(usuarioEncontrado);
                            return ResponseEntity.ok(dto);
                        })
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping
    public ResponseEntity<Object> deletarUsuario(
            @RequestParam(value = "id", required = false) UUID id,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade,
            @RequestParam(value = "dataNascimento", required = false) LocalDate dataNascimento
    ) {

        try {
            Optional<UsuarioEntity> entity = service.deletarComFiltro(id, nome, nacionalidade, dataNascimento);
            return entity
                    .map(entityEncontrado -> {
                        service.deletarUsuario(entityEncontrado);
                        return ResponseEntity.noContent().build();
                    })
                    .orElseThrow(() -> new OperacaoNaoPermitida("Entidade não existe"));
        } catch (OperacaoNaoPermitida e) {

            var erro = MensagemErro.naoEncontrado(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);

        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") UUID id,
            @RequestBody @Valid UsuarioDTO dto
    ) {
        try {

            service.buscarPorId(id)
                    .map(usuarioEncontrado -> {
                        usuarioEncontrado.setNome(dto.nome());
                        usuarioEncontrado.setNacionalidade(dto.nacionalidade());
                        usuarioEncontrado.setDataNascimento(dto.dataNascimento());

                        service.salvar(usuarioEncontrado);

                        UsuarioDTO usuarioEncontradoDTO = mapper.toDTO(usuarioEncontrado);
                        return ResponseEntity.ok(usuarioEncontradoDTO);
                    })
                    .orElseThrow(() -> new OperacaoNaoPermitida("Usuario não encontrado"));
        } catch (OperacaoNaoPermitida e) {

            var erro = MensagemErro.conflito(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);

            }
        return ResponseEntity.notFound().build();
        }

}
