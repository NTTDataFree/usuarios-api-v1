package com.nttdata.demo.agentes.service.impl;

import com.nttdata.demo.agentes.model.dto.UsuarioDto;
import com.nttdata.demo.agentes.model.entity.Usuario;
import com.nttdata.demo.agentes.model.mapper.UsuarioMapper;
import com.nttdata.demo.agentes.repository.UsuarioRepository;
import com.nttdata.demo.agentes.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Flux<UsuarioDto> findAll() {
        log.info("Buscando todos los usuarios");
        return usuarioRepository.findAll()
                .map(usuarioMapper::toDto)
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(e -> {
                    log.error("Error en findAll", e);
                    return Flux.empty();
                });
    }

    @Override
    public Mono<UsuarioDto> findById(Long id) {
        log.info("Buscando usuario por id: {}", id);
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDto)
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(e -> {
                    log.error("Error en findById", e);
                    return Mono.empty();
                });
    }

    @Override
    public Mono<UsuarioDto> create(UsuarioDto usuarioDto) {
        log.info("Creando usuario: {}", usuarioDto);
        Usuario usuario = usuarioMapper.toEntity(usuarioDto);
        return usuarioRepository.save(usuario)
                .map(usuarioMapper::toDto)
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(e -> {
                    log.error("Error en create", e);
                    return Mono.empty();
                });
    }

    @Override
    public Mono<UsuarioDto> update(Long id, UsuarioDto usuarioDto) {
        log.info("Actualizando usuario id: {} con datos: {}", id, usuarioDto);
        return usuarioRepository.findById(id)
                .flatMap(existing -> {
                    usuarioMapper.updateEntityFromDto(usuarioDto, existing);
                    return usuarioRepository.save(existing);
                })
                .map(usuarioMapper::toDto)
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(e -> {
                    log.error("Error en update", e);
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.info("Eliminando usuario id: {}", id);
        return usuarioRepository.deleteById(id)
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(e -> log.error("Error en delete", e));
    }
}
