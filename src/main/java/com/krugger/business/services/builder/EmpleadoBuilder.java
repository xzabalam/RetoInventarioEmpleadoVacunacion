package com.krugger.business.services.builder;

import java.util.Date;

import com.krugger.data.entities.Empleado;
import com.krugger.data.enums.EstadoVacunacionEnum;
import com.krugger.data.enums.TipoVacunaEnum;

public class EmpleadoBuilder {

	private static Empleado empleado;

	public final static EmpleadoBuilder newBuilder() {
		empleado = new Empleado();
		return new EmpleadoBuilder();
	}

	public EmpleadoBuilder apellidos(String apellidos) {
		empleado.setApellidos(apellidos);
		return this;
	}

	public Empleado build() {
		final Date fecha = new Date();
		empleado.setFechaCreacion(fecha);
		return empleado;
	}

	public EmpleadoBuilder cedula(String cedula) {
		empleado.setCedula(cedula);
		return this;
	}

	public EmpleadoBuilder email(String email) {
		empleado.setEmail(email);
		return this;
	}

	public EmpleadoBuilder estadoVacunacion(EstadoVacunacionEnum estadoVacunacion) {
		empleado.setEstadoVacunado(estadoVacunacion);
		return this;
	}

	public EmpleadoBuilder fechaVacunacion(Date fechaVacunacion) {
		empleado.setFechaVacunacion(fechaVacunacion);
		return this;
	}

	public EmpleadoBuilder nombres(String nombres) {
		empleado.setNombres(nombres);
		return this;
	}

	public EmpleadoBuilder numeroDosis(Integer numeroDosis) {
		empleado.setNumeroDosis(numeroDosis);
		return this;
	}

	public EmpleadoBuilder tipoVacuna(TipoVacunaEnum tipoVacuna) {
		empleado.setTipoVacuna(tipoVacuna);
		return this;
	}
}
