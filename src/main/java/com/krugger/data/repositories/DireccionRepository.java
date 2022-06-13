package com.krugger.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.krugger.data.entities.Direccion;

/**
 * Repositorio para {@link Direccion}
 *
 * @author xzabalam
 *
 */
@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {

	@Query("select e.domicilio from Empleado e where e.id = ?1")
	List<Direccion> findAllByEmpleado(Long idEmpleado);
}
