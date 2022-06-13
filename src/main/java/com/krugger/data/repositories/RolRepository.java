package com.krugger.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krugger.data.entities.Rol;

/**
 * Repositorio para {@link Rol}
 *
 * @author xzabalam
 *
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

	Optional<Rol> findByNombre(String nombre);
}
