package com.nttdata.demo.agentes.model.mapper;

import com.nttdata.demo.agentes.model.dto.UsuarioDto;
import com.nttdata.demo.agentes.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioDto toDto(Usuario usuario);
    Usuario toEntity(UsuarioDto usuarioDto);
    void updateEntityFromDto(UsuarioDto dto, @MappingTarget Usuario entity);
}
