package io.github.danilodantas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.danilodantas.domain.entity.Usuario;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Integer> {

}
