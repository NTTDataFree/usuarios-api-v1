package com.nttdata.demo.agentes.controller;

import com.nttdata.demo.agentes.model.dto.UsuarioDto;
import com.nttdata.demo.agentes.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable("usuarios")
    public Flux<UsuarioDto> getAllUsuarios() {
        log.info("[GET] /usuarios - Consultando todos los usuarios");
        return usuarioService.findAll()
                .doOnNext(u -> log.info("Usuario encontrado: {}", u))
                .doOnError(e -> log.error("Error al consultar usuarios", e));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "usuarios", key = "#id")
    public Mono<UsuarioDto> getUsuarioById(@PathVariable Long id) {
        log.info("[GET] /usuarios/{} - Consultando usuario por id", id);
        return usuarioService.findById(id)
                .doOnSuccess(u -> log.info("Usuario encontrado: {}", u))
                .doOnError(e -> log.error("Error al consultar usuario por id", e));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UsuarioDto> createUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        log.info("[POST] /usuarios - Creando usuario: {}", usuarioDto);
        return usuarioService.create(usuarioDto)
                .doOnSuccess(u -> log.info("Usuario creado: {}", u))
                .doOnError(e -> log.error("Error al crear usuario", e));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "usuarios", key = "#id")
    public Mono<UsuarioDto> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDto usuarioDto) {
        log.info("[PUT] /usuarios/{} - Actualizando usuario: {}", id, usuarioDto);
        return usuarioService.update(id, usuarioDto)
                .doOnSuccess(u -> log.info("Usuario actualizado: {}", u))
                .doOnError(e -> log.error("Error al actualizar usuario", e));
    }

    @DeleteMapping(value = "/{id}")
    @CacheEvict(value = "usuarios", key = "#id")
    public Mono<Void> deleteUsuario(@PathVariable Long id) {
        log.info("[DELETE] /usuarios/{} - Eliminando usuario", id);
        return usuarioService.delete(id)
                .doOnSuccess(v -> log.info("Usuario eliminado: {}", id))
                .doOnError(e -> log.error("Error al eliminar usuario", e));
    }
}
