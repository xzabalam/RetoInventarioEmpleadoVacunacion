package com.krugger.business.services.builder;

import java.util.Calendar;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.krugger.data.entities.Usuario;

/**
 * Permite generar una nueva instancia de un usuario con base en los nombres y
 * apellidos del empleado.
 *
 * @author xzabalam
 *
 */
public class UsuarioBuilder {

	public static final String getEncode(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

	public static final UsuarioBuilder newBuilder() {
		return new UsuarioBuilder();
	}

	private String nombres = "";
	private String apellidos = "";

	private String usernameCrea = "";

	public UsuarioBuilder apellidos(String apellidos) {
		this.apellidos = apellidos;
		return this;
	}

	/**
	 * Se genera un usuario cuyo username y password se generan con base en los
	 * nombres y apelllidos del empleado, el password será el mismo username
	 * codificado.
	 *
	 * @return
	 */
	public Usuario build() {
		final String username = generateUsername();
		final String password = getEncode(username);

		final Usuario usuario = new Usuario(username, password);
		usuario.setUsernameCrea(usernameCrea);
		usuario.setFechaCreacion(new Date());

		return usuario;
	}

	public UsuarioBuilder nombres(String nombres) {
		this.nombres = nombres;
		return this;
	}

	public UsuarioBuilder usernameCrea(String username) {
		usernameCrea = username;
		return this;
	}

	private String generatarFecha() {
		final Calendar calendar = Calendar.getInstance();
		final Integer dia = calendar.get(Calendar.DAY_OF_MONTH);
		final Integer mes = calendar.get(Calendar.MONTH);
		final Integer anio = calendar.get(Calendar.YEAR);

		return String.format("%d%d%d", dia, mes, anio);
	}

	/**
	 * El nombre de usuario se generara uniendo la primera letra de cada nombre y de
	 * cada apellido, más la fecha actual en formato día mes año.
	 *
	 * <p>
	 * Ejemplo: Francisco Xavier Zabala Miranda -> fxzm11062022
	 *
	 * @return username
	 */
	private String generateUsername() {
		final String primeraParteDelUsername = getFirstLetter(nombres);
		final String segundaParteDelUsername = getFirstLetter(apellidos);
		final String fecha = generatarFecha();
		return String.format("%s%s%s", primeraParteDelUsername, segundaParteDelUsername, fecha);
	}

	private String getFirstLetter(String nombres) {
		final String[] arrayItems = nombres.trim().toLowerCase().split(" ");
		final StringBuilder firstLetters = new StringBuilder();
		for (final String item : arrayItems) {
			firstLetters.append(item.substring(0, 1));
		}
		return firstLetters.toString();
	}
}
