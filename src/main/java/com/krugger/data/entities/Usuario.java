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
@Table(name = "usuario", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class Usuario extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = -7310305185273678943L;

	@NotEmpty
	@Column(name = "username", nullable = false)
	@Size(min = 5, max = 20, message = "{entity.auditoria.username}")
	private String username;

	@NotEmpty
	// @PasswordValido
	@Column(name = "password", nullable = false)
	private String password;

	public Usuario() {
	}

	public Usuario(@NotEmpty @Size(min = 10, max = 20, message = "{entity.auditoria.username}") String username,
			@NotEmpty String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || getClass() != obj.getClass()) {
			return false;
		}
		final Usuario other = (Usuario) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		final int result = super.hashCode();
		return prime * result + Objects.hash(password, username);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Usuario [" + (username != null ? "username=" + username + ", " : "")
				+ (password != null ? "password=" + password : "") + "]";
	}
}
