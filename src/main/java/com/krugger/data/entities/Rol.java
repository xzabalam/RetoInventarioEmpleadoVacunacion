package com.krugger.data.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.krugger.data.entities.core.impl.AbstractEntity;

@Entity
@Table(name = "rol", uniqueConstraints = { @UniqueConstraint(columnNames = { "nombre" }) })
public class Rol extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -2444040665252984879L;

	@NotEmpty
	@Column(name = "nombre", nullable = false)
	@Size(min = 4, max = 20, message = "{entity.rol.nombre.size}")
	private String nombre;

	@NotEmpty
	@Column(name = "descripcion", nullable = false)
	@Size(min = 3, max = 250, message = "{entity.rol.descripcion.size}")
	private String descripcion;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || getClass() != obj.getClass()) {
			return false;
		}
		final Rol other = (Rol) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(nombre, other.nombre);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		final int result = super.hashCode();
		return prime * result + Objects.hash(descripcion, nombre);
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Rol [" + (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (descripcion != null ? "descripcion=" + descripcion : "") + "]";
	}
}
