package com.krugger.data.entities.core.impl;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krugger.data.entities.core.Auditable;
import com.krugger.data.entities.core.Entity;

/**
 * Clase abstracta que contiene toda la información común a todas las entidades.
 *
 * @author xzabalam
 *
 */
@MappedSuperclass
public abstract class AbstractEntity implements Entity, Auditable {
	private static final long serialVersionUID = -412218241272244613L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "fecha_creacion", nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonIgnore
	private Date fechaCreacion;

	@Column(name = "fecha_modificacion")
	@Temporal(TemporalType.DATE)
	@JsonIgnore
	private Date fechaModificacion;

	@NotEmpty
	@Column(name = "username_crea", nullable = false)
	@Size(min = 5, max = 20, message = "{entity.auditoria.username}")
	@JsonIgnore
	private String usernameCrea;

	@Column(name = "username_modifica")
	@Size(min = 5, max = 20, message = "{entity.auditoria.username}")
	@JsonIgnore
	private String usernameModifica;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final AbstractEntity other = (AbstractEntity) obj;
		return Objects.equals(fechaCreacion, other.fechaCreacion)
				&& Objects.equals(fechaModificacion, other.fechaModificacion) && Objects.equals(id, other.id)
				&& Objects.equals(usernameCrea, other.usernameCrea)
				&& Objects.equals(usernameModifica, other.usernameModifica);
	}

	@Override
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	@Override
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getUsernameCrea() {
		return usernameCrea;
	}

	@Override
	public String getUsernameModifica() {
		return usernameModifica;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaCreacion, fechaModificacion, id, usernameCrea, usernameModifica);
	}

	@Override
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setUsernameCrea(String usernameCrea) {
		this.usernameCrea = usernameCrea;
	}

	@Override
	public void setUsernameModifica(String usernameModifica) {
		this.usernameModifica = usernameModifica;
	}

	@Override
	public String toString() {
		return "AbstractEntity [" + (id != null ? "id=" + id + ", " : "")
				+ (fechaCreacion != null ? "fechaCreacion=" + fechaCreacion + ", " : "")
				+ (fechaModificacion != null ? "fechaModificacion=" + fechaModificacion + ", " : "")
				+ (usernameCrea != null ? "usernameCrea=" + usernameCrea + ", " : "")
				+ (usernameModifica != null ? "usernameModifica=" + usernameModifica : "") + "]";
	}
}
