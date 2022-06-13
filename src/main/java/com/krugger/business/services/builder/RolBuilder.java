package com.krugger.business.services.builder;

import java.util.Date;

import com.krugger.data.entities.Rol;

public class RolBuilder {

	private static Rol rol;

	public static RolBuilder newBuilder() {
		rol = new Rol();
		return new RolBuilder();
	}

	public Rol build() {
		rol.setFechaCreacion(new Date());
		return rol;
	}

	public RolBuilder descripcion(String nombre) {
		rol.setDescripcion(nombre);
		return this;
	}

	public RolBuilder nombre(String nombre) {
		rol.setNombre(nombre);
		return this;
	}

	public RolBuilder usuarioCrea(String usuarioCrea) {
		rol.setUsernameCrea(usuarioCrea);
		return this;
	}
}
