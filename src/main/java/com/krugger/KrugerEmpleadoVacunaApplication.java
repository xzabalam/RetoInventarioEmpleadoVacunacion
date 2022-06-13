package com.krugger;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.server.ResponseStatusException;

import com.github.javafaker.Faker;
import com.krugger.business.services.EmpleadoService;
import com.krugger.business.services.RolService;
import com.krugger.business.services.UsuarioRolService;
import com.krugger.business.services.builder.EmpleadoBuilder;
import com.krugger.business.services.builder.RolBuilder;
import com.krugger.data.entities.Empleado;
import com.krugger.data.entities.Rol;
import com.krugger.data.entities.UsuarioRol;
import com.krugger.data.enums.EstadoVacunacionEnum;
import com.krugger.data.enums.TipoVacunaEnum;

@SpringBootApplication
public class KrugerEmpleadoVacunaApplication implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(KrugerEmpleadoVacunaApplication.class);
	private static final String FAKE_USER = "admin";
	private static final String ADMIIN_USER = "Administrador";

	public static void main(String[] args) {
		SpringApplication.run(KrugerEmpleadoVacunaApplication.class, args);
	}

	@Autowired
	private EmpleadoService empleadoService;

	@Autowired
	private RolService rolService;

	@Autowired
	private UsuarioRolService usuarioRolService;

	@Autowired
	private Faker faker;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			// Sirve para verificar si existe creado el usuario administrador en la base de
			// datos, si no se generar[a una excepci[on y se creara el usuario administradir
			empleadoService.getEmpleadoPorNombreYApellido(ADMIIN_USER, ADMIIN_USER);
		} catch (final ResponseStatusException e) {
			crearDatosIniciales();
		}

	}

	private void crearDatosIniciales() {
		// Crear los roles
		final Rol rolAdministrador = RolBuilder.newBuilder().nombre(ADMIIN_USER.toUpperCase())
				.descripcion("Administrador del sistema").usuarioCrea("admin").build();

		rolService.createRol(rolAdministrador);

		log.info(rolAdministrador.toString());

		final Rol rolEmpleado = RolBuilder.newBuilder().nombre("EMPLEADO")
				.descripcion("Empleado de la empresa, usuario normal.").usuarioCrea("admin").build();

		rolService.createRol(rolEmpleado);

		log.info(rolEmpleado.toString());

		// Crear empleado y usuario administrador
		final Empleado empleado = EmpleadoBuilder.newBuilder().cedula("0100000000").nombres("Administrador")
				.apellidos("Administrador").email("admin@kruger.com").build();

		empleadoService.createEmpleado(empleado, FAKE_USER);

		log.info(empleado.toString());

		// Asignar el rol administrador al usuario administrador
		final UsuarioRol usuarioRolAdministrador = new UsuarioRol(empleado.getUsuario(), rolAdministrador);
		usuarioRolAdministrador.setFechaCreacion(new Date());
		usuarioRolAdministrador.setUsernameCrea(empleado.getUsernameCrea());
		usuarioRolService.crearUsuarioRol(usuarioRolAdministrador);

		log.info(usuarioRolAdministrador.toString());

		final TipoVacunaEnum[] valuesTipoVacuna = TipoVacunaEnum.values();
		final EstadoVacunacionEnum[] valuesEstadoVacunacion = EstadoVacunacionEnum.values();

		// Llenamos con datos ficticios
		final long cedula = 1000000000L;
		for (int i = 0; i < 100; i++) {
			try {
				final Empleado empleadoNuevo = EmpleadoBuilder.newBuilder().cedula(String.valueOf(cedula + i))
						.nombres(faker.name().firstName()).apellidos(faker.name().lastName())
						.estadoVacunacion(valuesEstadoVacunacion[new Random().nextInt(2)])
						.tipoVacuna(valuesTipoVacuna[new Random().nextInt(4)]).numeroDosis(new Random().nextInt(4))
						.fechaVacunacion(new Date(ThreadLocalRandom.current().nextInt() * 1000L))
						.email(faker.internet().emailAddress()).build();

				empleadoService.createEmpleado(empleadoNuevo, "admin");

				log.info(empleadoNuevo.toString());
			} catch (final Exception e) {
			}

		}
	}

}
