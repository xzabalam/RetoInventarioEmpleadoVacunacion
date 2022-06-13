package com.krugger.business.services;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.krugger.business.services.builder.UsuarioBuilder;
import com.krugger.data.entities.Direccion;
import com.krugger.data.entities.Empleado;
import com.krugger.data.entities.Usuario;
import com.krugger.data.entities.UsuarioRol;
import com.krugger.data.enums.EstadoVacunacionEnum;
import com.krugger.data.enums.TipoVacunaEnum;
import com.krugger.data.repositories.EmpleadoRepository;

/**
 * Servicio que permite obtener la data de los empleados
 *
 * @author xzabalam
 *
 */
@Service
public class EmpleadoService {

	private static final Logger log = LoggerFactory.getLogger(EmpleadoService.class);

	@Autowired
	private EmpleadoRepository empleadoRepository;

	@Autowired
	private DireccionService direccionService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRolService usuarioRolService;

	/**
	 * Permite crear un empleado
	 *
	 * @param empleado
	 *
	 * @return
	 */
	@Secured({ "ROLE_ADMININISTRADOR" })
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Empleado createEmpleado(Empleado empleado, String usernameCrea) {
		final Optional<Empleado> result = empleadoRepository.findByCedula(empleado.getCedula());

		if (result.isPresent()) {
			return result.get();
		}

		// Creamos el usuario que se relacionará al empleado.
		final Usuario usuario = UsuarioBuilder.newBuilder().nombres(empleado.getNombres())
				.apellidos(empleado.getApellidos()).usernameCrea(usernameCrea).build();

		usuarioService.createUser(usuario);

		empleado.setUsernameCrea(usernameCrea);
		empleado.setFechaCreacion(new Date());
		empleado.setUsuario(usuario);

		if (Objects.isNull(empleado.getEstadoVacunado())) {
			empleado.setEstadoVacunado(EstadoVacunacionEnum.NO_VACUNADO);
		}

		final UsuarioRol crearUsuarioRolEmpleado = usuarioRolService.crearUsuarioRolEmpleado(usuario);
		log.info(crearUsuarioRolEmpleado.toString());

		return empleadoRepository.save(empleado);
	}

	/**
	 * Permite eliminar un empleado dado si id
	 *
	 * @param idEmpleado
	 */
	@Secured({ "ROLE_ADMININISTRADOR" })
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteEmpleado(Long idEmpleado) {
		final Optional<Empleado> result = empleadoRepository.findById(idEmpleado);

		if (result.isPresent()) {
			empleadoRepository.delete(result.get());
		}

		throw new ResponseStatusException(HttpStatus.NOT_FOUND,
				String.format("{service.empleado.no.existe}", idEmpleado));
	}

	@Secured({ "ROLE_ADMININISTRADOR" })
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteEmpleadoByCedula(String cedula) {
		final Usuario usuario = usuarioService.getUserByCedulaEmpleado(cedula);
		final Empleado empleado = getEmpleadoPorCedula(cedula);

		empleadoRepository.delete(empleado);

		usuarioService.deleteUsuario(usuario);
	}

	@Secured({ "ROLE_ADMININISTRADOR" })
	public void deleteEmpleadoPorCedula(String cedula) {
		deleteEmpleadoByCedula(cedula);
	}

	/**
	 * Obtiene todas las direcciones almacenaas para un empleado
	 *
	 * @param idEmpleado
	 *
	 * @return
	 */
	@Secured({ "ROLE_ADMININISTRADOR", "ROL_EMPLEADO" })
	public List<Direccion> getDireccionesByEmpleado(Long idEmpleado) {
		return direccionService.getDireccionesByEmpleado(idEmpleado);
	}

	/**
	 * Permite buscar un empleado por cedula
	 *
	 * @param cedula
	 *
	 * @return
	 */
	@Secured({ "ROLE_ADMININISTRADOR", "ROL_EMPLEADO" })
	public Empleado getEmpleadoPorCedula(String cedula) {
		return empleadoRepository.findByCedula(cedula)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("{service.empleado.no.existe.por.cedula}", cedula)));
	}

	/**
	 * Permite buscar un empleado por el id
	 *
	 * @param idEmpleado
	 *
	 * @return
	 */
	@Secured({ "ROLE_ADMININISTRADOR", "ROL_EMPLEADO" })
	public Empleado getEmpleadoPorId(Long idEmpleado) {
		return empleadoRepository.findById(idEmpleado)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("{service.empleado.no.existe.por.id}", idEmpleado)));
	}

	/**
	 * Permite buscar un empleado por cedula
	 *
	 * @param cedula
	 *
	 * @return
	 */
	@Secured({ "ROLE_ADMININISTRADOR", "ROL_EMPLEADO" })
	public Empleado getEmpleadoPorNombreYApellido(String nombres, String apellidos) {
		return empleadoRepository.findByNombresAndApellidos(nombres, apellidos)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("{service.empleado.no.existe.por.nombres.apellidos}", nombres, apellidos)));
	}

	/**
	 * Permite buscar un empleado por cedula
	 *
	 * @param cedula
	 *
	 * @return
	 */
	@Secured({ "ROLE_ADMININISTRADOR", "ROL_EMPLEADO" })
	public Empleado getEmpleadoPorUsername(String username) {
		return empleadoRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("{service.empleado.no.existe.por.username}", username)));
	}

	/**
	 * Obtiene los empleados con paginación
	 *
	 * @param page Página inicial
	 * @param size Tamaño de elementos por página
	 *
	 * @return
	 */
	@Secured({ "ROLE_ADMININISTRADOR", "ROL_EMPLEADO" })
	public Page<Empleado> getEmpleados(int page, int size, EstadoVacunacionEnum estadoVacuna, TipoVacunaEnum tipoVacuna,
			Date fechaDesde, Date fechaHasta) {

		if (Objects.isNull(estadoVacuna) && Objects.isNull(tipoVacuna) && Objects.isNull(fechaDesde)
				&& Objects.isNull(fechaHasta)) {
			return empleadoRepository.findAll(PageRequest.of(page, size));
		}

		// Cuando se busca por estado vacunado, se puede buscar por el tipo de vacuna y
		// por el rango de fechas
		if (!Objects.isNull(estadoVacuna) && EstadoVacunacionEnum.VACUNADO.equals(estadoVacuna)) {
			// Cuando se a;ade tambi[en el tipo de vacuna y no el rango de fechas
			if (!Objects.isNull(tipoVacuna) && Objects.isNull(fechaDesde) && Objects.isNull(fechaHasta)) {
				return empleadoRepository.findByEstadoVacunadoAndTipoVacuna(estadoVacuna, tipoVacuna,
						PageRequest.of(page, size));
			}

			// Cuando se a;ade el tipo de vacuna, la fechaDesde y no la FechaHasta, se debe
			// buscar hasta la fecha actual
			if (!Objects.isNull(tipoVacuna) && !Objects.isNull(fechaDesde) && Objects.isNull(fechaHasta)) {
				return empleadoRepository.findByEstadoVacunadoAndTipoVacunaAndRangoFechas(estadoVacuna, tipoVacuna,
						fechaDesde, new Date(), PageRequest.of(page, size));
			}

			// Cuando se a;ade el tipo de vacuna, la fechaDesde y la FechaHasta
			if (!Objects.isNull(tipoVacuna) && !Objects.isNull(fechaDesde) && !Objects.isNull(fechaHasta)) {
				return empleadoRepository.findByEstadoVacunadoAndTipoVacunaAndRangoFechas(estadoVacuna, tipoVacuna,
						fechaDesde, fechaHasta, PageRequest.of(page, size));
			}

			// Si no se agregan el tipo de vacunaci[on ni el rango de fechas, se debe
			// obtener solo el estado de vacuna
			return empleadoRepository.findByEstadoVacunacion(estadoVacuna, PageRequest.of(page, size));
		}

		if (!Objects.isNull(estadoVacuna) && EstadoVacunacionEnum.NO_VACUNADO.equals(estadoVacuna)) {
			// Si el estado es NO_VACUNADO no se debe filtrar por nada m[as.
			return empleadoRepository.findByEstadoVacunacion(estadoVacuna, PageRequest.of(page, size));
		}

		return empleadoRepository.findAll(PageRequest.of(page, size));
	}

	/**
	 * Permite actualizar un empleado
	 *
	 * @param idEmpleado
	 * @param empleado
	 *
	 * @return
	 */
	@Secured({ "ROLE_ADMININISTRADOR", "ROL_EMPLEADO" })
	public Empleado updateEmpleado(Long idEmpleado, Empleado empleado) {
		final Optional<Empleado> result = empleadoRepository.findById(idEmpleado);

		if (result.isPresent()) {
			return result.get();
		}

		return empleadoRepository.save(empleado);
	}

	@Secured({ "ROLE_ADMININISTRADOR", "ROL_EMPLEADO" })
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Empleado updateEmpleado(String cedula, Empleado empleado, String usernameCreaModifica) {
		final Optional<Empleado> result = empleadoRepository.findByCedula(cedula);

		if (result.isPresent()) {
			final Empleado empleadoRecuperado = result.get();
			empleado.setId(empleadoRecuperado.getId());
			comprobarFechas(empleado, empleadoRecuperado, usernameCreaModifica);
			comprobarUsuarios(empleado, empleadoRecuperado, usernameCreaModifica);
			empleado.setUsuario(empleadoRecuperado.getUsuario());
			return empleadoRepository.save(empleado);
		}

		throw new ResponseStatusException(HttpStatus.NOT_FOUND,
				String.format("{service.empleado.no.existe.por.cedula}", cedula));
	}

	private void comprobarFechas(Empleado empleado, Empleado empleadoRecuperado, String usernameCreaModifica) {
		final Date fechaCreaModifica = new Date();

		// comprobar las fechas de creacion
		if (Objects.isNull(empleado.getFechaCreacion())) {
			empleado.setFechaCreacion(fechaCreaModifica);
		} else {
			empleado.setFechaCreacion(empleadoRecuperado.getFechaCreacion());
		}

		// comprobar las fechas de modificacion
		if (Objects.isNull(empleado.getFechaModificacion())) {
			empleado.setFechaModificacion(fechaCreaModifica);
		} else {
			empleado.setFechaModificacion(empleadoRecuperado.getFechaModificacion());
		}

		// comprobar las fechas de creacion del domicilio
		if (Objects.isNull(empleado.getDomicilio().getFechaCreacion())) {
			empleado.getDomicilio().setFechaCreacion(fechaCreaModifica);
		} else {
			empleado.getDomicilio().setFechaCreacion(empleadoRecuperado.getDomicilio().getFechaCreacion());
		}

		// comprobar las fechas de modificacion del domicilio
		if (Objects.isNull(empleado.getDomicilio().getFechaModificacion())) {
			empleado.getDomicilio().setFechaModificacion(fechaCreaModifica);
		} else {
			empleado.getDomicilio().setFechaModificacion(empleadoRecuperado.getDomicilio().getFechaModificacion());
		}

		// comprobar las fechas de creacion del domicilio
		if (Objects.isNull(empleado.getDomicilio().getUsernameCrea())) {
			empleado.getDomicilio().setUsernameCrea(usernameCreaModifica);
		} else {
			empleado.getDomicilio().setUsernameModifica(usernameCreaModifica);
		}

		// comprobar las fechas de creacion del domicilio
		if (Objects.isNull(empleado.getDomicilio().getUsernameCrea())) {
			empleado.getDomicilio().setUsernameCrea(usernameCreaModifica);
		} else {
			empleado.getDomicilio().setUsernameModifica(usernameCreaModifica);
		}

	}

	private void comprobarUsuarios(Empleado empleado, Empleado empleadoRecuperado, String usernameCreaModifica) {
		// comprobar el usuario de creacion
		if (Objects.isNull(empleado.getUsernameCrea())) {
			empleado.setUsernameCrea(usernameCreaModifica);
		} else {
			empleado.setUsernameCrea(empleadoRecuperado.getUsernameCrea());
		}

		// comprobar el usuario de modifica
		if (Objects.isNull(empleado.getUsernameModifica())) {
			empleado.setUsernameModifica(usernameCreaModifica);
		} else {
			empleado.setUsernameModifica(empleadoRecuperado.getUsernameModifica());
		}

		// comprobar el usuario de creacion del domicilio
		if (Objects.isNull(empleado.getDomicilio().getUsernameCrea())) {
			empleado.getDomicilio().setUsernameCrea(usernameCreaModifica);
		} else {
			empleado.getDomicilio().setUsernameCrea(empleadoRecuperado.getUsernameCrea());
		}

		// comprobar el usuario de modifica del domicilio
		if (Objects.isNull(empleado.getDomicilio().getUsernameModifica())) {
			empleado.getDomicilio().setUsernameModifica(usernameCreaModifica);
		} else {
			empleado.getDomicilio().setUsernameModifica(empleadoRecuperado.getUsernameModifica());
		}
	}
}
