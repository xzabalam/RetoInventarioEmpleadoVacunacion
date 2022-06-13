package com.krugger.web.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krugger.business.services.EmpleadoService;
import com.krugger.data.entities.Empleado;
import com.krugger.data.enums.EstadoVacunacionEnum;
import com.krugger.data.enums.TipoVacunaEnum;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

	@Autowired
	private EmpleadoService empleadoService;

	@PostMapping
	@Operation(summary = "Crear un nuevo empleado, solo un administrador puede hacerlo.")
	public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleado) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return new ResponseEntity<>(empleadoService.createEmpleado(empleado, authentication.getName()),
				HttpStatus.CREATED);
	}

	@DeleteMapping("/{cedula}")
	@Operation(summary = "Eliminar un empleado existente, solo un administrador puede hacerlo.")
	public ResponseEntity<Void> deleteUserByCedula(@PathVariable("cedula") String cedula) {
		empleadoService.deleteEmpleadoPorCedula(cedula);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{cedula}")
	@Operation(summary = "Buscar un empleado por el número de cédula, un administrador y un empleado pueden hacerlo.")
	public ResponseEntity<Empleado> getEmpleadoByCedula(@PathVariable("cedula") String cedula) {
		return new ResponseEntity<>(empleadoService.getEmpleadoPorCedula(cedula), HttpStatus.OK);
	}

	@GetMapping
	@Operation(summary = "Listar todos los empleados, solo un administrador puede hacerlo.")
	public ResponseEntity<Page<Empleado>> getEmpleados(
			@RequestParam(required = false, value = "page", defaultValue = "0") int page,
			@RequestParam(required = false, value = "size", defaultValue = "500") int size,
			@RequestParam(required = false, value = "estadoVacunacion") EstadoVacunacionEnum estadoVacunacion,
			@RequestParam(required = false, value = "tipoVacuna") TipoVacunaEnum tipoVacuna,
			@RequestParam(required = false, value = "fechaDesde")
			@DateTimeFormat(pattern = "yyyy-mm-dd") Date fechaDesde,
			@RequestParam(required = false, value = "fechaHasta")
			@DateTimeFormat(pattern = "yyyy-mm-dd") Date fechaHasta) {
		return new ResponseEntity<>(
				empleadoService.getEmpleados(page, size, estadoVacunacion, tipoVacuna, fechaDesde, fechaHasta),
				HttpStatus.OK);
	}

	@PutMapping(value = "/{cedula}")
	@Operation(summary = "Actualizar un empleado, un administrador y un empleado pueden hacerlo.")
	public ResponseEntity<Empleado> updateEmpleado(@PathVariable("cedula") String cedula,
			@RequestBody Empleado empleado) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return new ResponseEntity<>(empleadoService.updateEmpleado(cedula, empleado, authentication.getName()),
				HttpStatus.OK);
	}

}
