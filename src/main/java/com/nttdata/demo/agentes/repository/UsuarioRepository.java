package com.nttdata.demo.agentes.repository;

import com.nttdata.demo.agentes.model.entity.Usuario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends ReactiveCrudRepository<Usuario, Long> {
}
