package com.krugger.data.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.krugger.config.anotations.CedulaValida;
import com.krugger.data.entities.core.impl.AbstractEntity;
import com.krugger.data.enums.EstadoVacunacionEnum;
import com.krugger.data.enums.TipoVacunaEnum;

/**
 * Entidad Empleado
 *
 * @author xzabalam
 *
 */
@Entity
@Table(name = "empleado", uniqueConstraints = { @UniqueConstraint(columnNames = { "cedula" }) })
@JsonInclude(Include.NON_NULL)
public class Empleado extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 8900563733831336123L;

	@Column(name = "cedula", nullable = false)
	@CedulaValida(message = "{entity.empleado.cedula.invalida}")
	private String cedula;

	@NotEmpty
	@Column(name = "nombres", nullable = false)
	@Size(min = 3, max = 500, message = "{entity.empleado.nombre.size}")
	@Pattern(regexp = "^[A-Za-z ]*$", message = "{entity.empleado.solo.letras}")
	private String nombres;

	@NotEmpty
	@Column(name = "apellidos", nullable = false)
	@Size(min = 3, max = 500, message = "{entity.empleado.apellido.size}")
	@Pattern(regexp = "^[A-Za-z ]*$", message = "{entity.empleado.solo.letras}")
	private String apellidos;

	@Email(message = "{entity.empleado.email.not.valid}")
	@NotEmpty
	@Column(name = "email", nullable = false)
	private String email;

	@Past
	@Column(name = "fecha_nacimiento")
	@Temporal(TemporalType.DATE)
	private Date fecha_nacimiento;

	@Column(name = "celular")
	private String celular;

	@Column(name = "estado_vacunado", nullable = false)
	@Enumerated(EnumType.STRING)
	private EstadoVacunacionEnum estadoVacunado;

	@Column(name = "tipo_vacuna")
	@Enumerated(EnumType.STRING)
	private TipoVacunaEnum tipoVacuna;

	@Column(name = "fecha_vacunacion")
	@Temporal(TemporalType.DATE)
	private Date fechaVacunacion;

	@Column(name = "numero_dosis")
	private Integer numeroDosis;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "direccion_id", referencedColumnName = "id")
	private Direccion domicilio;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonIgnore
	private Usuario usuario;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || getClass() != obj.getClass()) {
			return false;
		}
		final Empleado other = (Empleado) obj;
		return Objects.equals(apellidos, other.apellidos) && Objects.equals(cedula, other.cedula)
				&& Objects.equals(celular, other.celular) && Objects.equals(domicilio, other.domicilio)
				&& Objects.equals(email, other.email) && estadoVacunado == other.estadoVacunado
				&& Objects.equals(fecha_nacimiento, other.fecha_nacimiento)
				&& Objects.equals(fechaVacunacion, other.fechaVacunacion) && Objects.equals(nombres, other.nombres)
				&& Objects.equals(numeroDosis, other.numeroDosis) && tipoVacuna == other.tipoVacuna
				&& Objects.equals(usuario, other.usuario);
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getCedula() {
		return cedula;
	}

	public String getCelular() {
		return celular;
	}

	public Direccion getDomicilio() {
		return domicilio;
	}

	public String getEmail() {
		return email;
	}

	public EstadoVacunacionEnum getEstadoVacunado() {
		return estadoVacunado;
	}

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public Date getFechaVacunacion() {
		return fechaVacunacion;
	}

	public String getNombres() {
		return nombres;
	}

	public Integer getNumeroDosis() {
		return numeroDosis;
	}

	public TipoVacunaEnum getTipoVacuna() {
		return tipoVacuna;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		final int result = super.hashCode();
		return prime * result + Objects.hash(apellidos, cedula, celular, domicilio, email, estadoVacunado,
				fecha_nacimiento, fechaVacunacion, nombres, numeroDosis, tipoVacuna, usuario);
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public void setDomicilio(Direccion domicilio) {
		this.domicilio = domicilio;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEstadoVacunado(EstadoVacunacionEnum estadoVacunado) {
		this.estadoVacunado = estadoVacunado;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public void setFechaVacunacion(Date fechaVacunacion) {
		this.fechaVacunacion = fechaVacunacion;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public void setNumeroDosis(Integer numeroDosis) {
		this.numeroDosis = numeroDosis;
	}

	public void setTipoVacuna(TipoVacunaEnum tipoVacuna) {
		this.tipoVacuna = tipoVacuna;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Empleado [" + (cedula != null ? "cedula=" + cedula + ", " : "")
				+ (nombres != null ? "nombres=" + nombres + ", " : "")
				+ (apellidos != null ? "apellidos=" + apellidos + ", " : "")
				+ (email != null ? "email=" + email + ", " : "")
				+ (fecha_nacimiento != null ? "fecha_nacimiento=" + fecha_nacimiento + ", " : "")
				+ (celular != null ? "celular=" + celular + ", " : "")
				+ (estadoVacunado != null ? "estadoVacunado=" + estadoVacunado + ", " : "")
				+ (tipoVacuna != null ? "tipoVacuna=" + tipoVacuna + ", " : "")
				+ (fechaVacunacion != null ? "fechaVacunacion=" + fechaVacunacion + ", " : "")
				+ (numeroDosis != null ? "numeroDosis=" + numeroDosis + ", " : "")
				+ (domicilio != null ? "domicilio=" + domicilio + ", " : "")
				+ (usuario != null ? "usuario=" + usuario : "") + "]";
	}
}
