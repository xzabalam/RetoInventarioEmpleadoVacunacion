package com.krugger.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.krugger.data.entities.Usuario;

/**
 * Repositorio para {@link Usuario}
 *
 * @author xzabalam
 *
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("select e.usuario from Empleado e where e.cedula = ?1")
	Optional<Usuario> findByCedulaEmpleado(String cedula);

	@Query("select e.usuario from Empleado e where e.id = ?1")
	Optional<Usuario> findByEmpleadoId(Long empleadoId);

	Optional<Usuario> findByUsername(String username);
}
