package com.krugger.business.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.krugger.data.entities.Usuario;
import com.krugger.data.repositories.UsuarioRepository;
import com.krugger.data.repositories.UsuarioRolRepository;

/**
 * Servicio que permite obtener la data de los empleados
 *
 * @author xzabalam
 *
 */
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioRolRepository usuarioRolRepository;

	/**
	 * Permite crear un usuario en la base de datos
	 *
	 * @param usuario TO que contiene toda la informaci[on de un uusario.
	 *
	 * @return el usuario creado.
	 */
	public Usuario createUser(Usuario usuario) {
		final Optional<Usuario> result = usuarioRepository.findByUsername(usuario.getUsername());

		if (result.isPresent()) {
			return result.get();
		}

		return usuarioRepository.save(usuario);
	}

	/**
	 * Permite eliminar el usuario buscado por el campo username
	 *
	 * @param username
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteUsuario(Usuario usuario) {
		usuarioRepository.delete(usuario);
	}

	/**
	 * Permite obtener un usuario buscado por el username, si no existe se generara
	 * una excepcion.
	 *
	 * @param username
	 *
	 * @return
	 */
	public Usuario getUserByCedulaEmpleado(String cedula) {
		return usuarioRepository.findByCedulaEmpleado(cedula)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("{service.usuario.por.cedula.no.existe}", cedula)));
	}

	/**
	 * Permite obtener un usuario basado en el id del empleado
	 *
	 * @param empleadoId
	 *
	 * @return
	 */
	public Usuario getUserByEmpleadoId(Long empleadoId) {
		return usuarioRepository.findByEmpleadoId(empleadoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("{service.usuario.no.existe}", empleadoId)));
	}

	/**
	 * Permite obtener un usuario buscado por el id, si no existe se generara una
	 * excepcion.
	 *
	 * @param userId
	 *
	 * @return
	 */
	public Usuario getUserById(Long userId) {
		return usuarioRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				String.format("{service.usuario.no.existe}", userId)));
	}

	/**
	 * Permite obtener un usuario buscado por el username, si no existe se generara
	 * una excepcion.
	 *
	 * @param username
	 *
	 * @return
	 */
	public Usuario getUserByUsername(String username) {
		return usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("{service.usuario.no.existe}", username)));
	}

	/**
	 * Permite obtener una lista de usuarios almacenados en la base de datos. Los
	 * resultados se devuelven con paginacion y ordenados ascendentemente
	 *
	 * @param page
	 * @param size
	 *
	 * @return
	 */
	public Page<Usuario> getUsuarios(int page, int size) {
		return usuarioRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "username")));
	}

	/**
	 * Obtiene los usuarios que tienen un rol en espec[ifico
	 *
	 * @param rol
	 *
	 * @return
	 */
	public List<Usuario> getUsuariosByRol(String rol) {
		return usuarioRolRepository.findUsersByRol(rol);
	}
}
