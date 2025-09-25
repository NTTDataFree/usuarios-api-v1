package com.nttdata.demo.agentes.service;

import com.nttdata.demo.agentes.model.dto.UsuarioDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsuarioService {
    Flux<UsuarioDto> findAll();
    Mono<UsuarioDto> findById(Long id);
    Mono<UsuarioDto> create(UsuarioDto usuarioDto);
    Mono<UsuarioDto> update(Long id, UsuarioDto usuarioDto);
    Mono<Void> delete(Long id);
}
