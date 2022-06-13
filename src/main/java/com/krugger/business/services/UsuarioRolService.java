package com.krugger.business.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.krugger.data.entities.Rol;
import com.krugger.data.entities.Usuario;
import com.krugger.data.entities.UsuarioRol;
import com.krugger.data.enums.RolEnum;
import com.krugger.data.repositories.RolRepository;
import com.krugger.data.repositories.UsuarioRolRepository;

/**
 * Servicio que permite obtener la data de los empleados
 *
 * @author xzabalam
 *
 */
@Service
public class UsuarioRolService {

	@Autowired
	private UsuarioRolRepository usuarioRolRepository;

	@Autowired
	private RolRepository rolRepository;

	public UsuarioRol crearUsuarioRol(UsuarioRol usuarioRol) {
		return usuarioRolRepository.save(usuarioRol);
	}

	public UsuarioRol crearUsuarioRolEmpleado(Usuario usuario) {
		final Optional<Rol> rolEmpleado = rolRepository.findByNombre(RolEnum.EMPLEADO.name());

		if (rolEmpleado.isPresent()) {
			final UsuarioRol usuarioRolEmpleado = new UsuarioRol(usuario, rolEmpleado.get());
			usuarioRolEmpleado.setFechaCreacion(new Date());
			usuarioRolEmpleado.setUsernameCrea(usuario.getUsername());

			return crearUsuarioRol(usuarioRolEmpleado);
		}

		throw new ResponseStatusException(HttpStatus.NOT_FOUND,
				String.format("El rol %s no existe", RolEnum.EMPLEADO.name()));
	}

	/**
	 * Permite eliminar el usuarioRol buscado por el campo username
	 *
	 * @param username
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteUserByUsuarioId(Long usuarioId) {
		final List<UsuarioRol> usuarioRoles = usuarioRolRepository.findByUsuarioId(usuarioId);
		usuarioRoles.forEach(usuarioRol -> usuarioRolRepository.delete(usuarioRol));
	}
}
