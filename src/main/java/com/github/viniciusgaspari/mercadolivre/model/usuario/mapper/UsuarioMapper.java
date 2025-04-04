package com.github.viniciusgaspari.mercadolivre.model.usuario.mapper;

import com.github.viniciusgaspari.mercadolivre.model.usuario.UsuarioEntity;
import com.github.viniciusgaspari.mercadolivre.model.usuario.dto.UsuarioDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioEntity toEntity(UsuarioDTO dto);

    UsuarioDTO toDTO(UsuarioEntity entity);

    List<UsuarioDTO> toListDTO(List<UsuarioEntity> entityList);

}
