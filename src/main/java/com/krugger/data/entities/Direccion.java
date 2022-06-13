package com.krugger.data.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.krugger.data.entities.core.impl.AbstractEntity;

@Entity
@Table(name = "direccion")
public class Direccion extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 7505385394540840962L;

	@NotEmpty
	@Column(name = "calle", nullable = false)
	@Size(min = 3, max = 250, message = "{entity.direccion.calle.size}")
	private String calle;

	@NotEmpty
	@Column(name = "numero", nullable = false)
	@Size(min = 3, max = 250, message = "{entity.direccion.numero.size}")
	private String numero;

	@NotEmpty
	@Column(name = "ciudad", nullable = false)
	@Size(min = 3, max = 250, message = "{entity.direccion.ciudad.size}")
	private String ciudad;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || getClass() != obj.getClass()) {
			return false;
		}
		final Direccion other = (Direccion) obj;
		return Objects.equals(calle, other.calle) && Objects.equals(ciudad, other.ciudad)
				&& Objects.equals(numero, other.numero);
	}

	public String getCalle() {
		return calle;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getNumero() {
		return numero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		final int result = super.hashCode();
		return prime * result + Objects.hash(calle, ciudad, numero);
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Direccion [" + (calle != null ? "calle=" + calle + ", " : "")
				+ (numero != null ? "numero=" + numero + ", " : "") + (ciudad != null ? "ciudad=" + ciudad : "") + "]";
	}

}