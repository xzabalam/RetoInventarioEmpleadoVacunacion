package com.krugger.data.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.krugger.data.entities.core.impl.AbstractEntity;

@Entity
@Table(name = "usuario_rol")
public class UsuarioRol extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 7633379909000709682L;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "rol_id", referencedColumnName = "id")
	private Rol rol;

	public UsuarioRol() {
	}

	public UsuarioRol(Usuario usuario, Rol rol) {
		this.usuario = usuario;
		this.rol = rol;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || getClass() != obj.getClass()) {
			return false;
		}
		final UsuarioRol other = (UsuarioRol) obj;
		return Objects.equals(rol, other.rol) && Objects.equals(usuario, other.usuario);
	}

	public Rol getRol() {
		return rol;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		final int result = super.hashCode();
		return prime * result + Objects.hash(rol, usuario);
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "UsuarioRol [" + (usuario != null ? "usuario=" + usuario + ", " : "") + (rol != null ? "rol=" + rol : "")
				+ "]";
	}

}
