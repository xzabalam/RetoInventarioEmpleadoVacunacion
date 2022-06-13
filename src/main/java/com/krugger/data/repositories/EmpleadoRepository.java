package com.krugger.data.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.krugger.data.entities.Empleado;
import com.krugger.data.enums.EstadoVacunacionEnum;
import com.krugger.data.enums.TipoVacunaEnum;

/**
 * Repositorio para {@link Empleado}
 *
 * @author xzabalam
 *
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>, JpaSpecificationExecutor<Empleado> {

	@Query("select e from Empleado e where e.cedula = ?1 ")
	Optional<Empleado> findByCedula(String cedula);

	@Query("select e from Empleado e where e.estadoVacunado = ?1 ")
	Page<Empleado> findByEstadoVacunacion(EstadoVacunacionEnum estadoVacunacionEnum, Pageable pageable);

	@Query("select e from Empleado e where e.estadoVacunado = ?1 and e.tipoVacuna = ?2")
	Page<Empleado> findByEstadoVacunadoAndTipoVacuna(EstadoVacunacionEnum estadoVacunacionEnum,
			TipoVacunaEnum tipoVacuna, Pageable pageable);

	@Query("select e from Empleado e where e.estadoVacunado = ?1 and e.tipoVacuna = ?2 and e.fechaVacunacion between ?3 and ?4")
	Page<Empleado> findByEstadoVacunadoAndTipoVacunaAndRangoFechas(EstadoVacunacionEnum estadoVacunacionEnum,
			TipoVacunaEnum tipoVacuna, Date fechaDesde, Date fechaHasta, Pageable pageable);

	Optional<Empleado> findByNombresAndApellidos(String nombres, String apellidos);

	@Query("select e from Empleado e where e.usuario.username = ?1 ")
	Optional<Empleado> findByUsername(String username);

}
