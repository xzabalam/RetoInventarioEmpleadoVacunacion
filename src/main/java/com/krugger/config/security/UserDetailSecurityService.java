package com.krugger.config.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.krugger.data.entities.Usuario;
import com.krugger.data.entities.UsuarioRol;
import com.krugger.data.repositories.UsuarioRepository;
import com.krugger.data.repositories.UsuarioRolRepository;

/**
 * Esta clase permite validar y autenticar a los usuarios registrados en mi base
 * de datos.
 *
 * @author xzabalam
 *
 */
@Service
public class UserDetailSecurityService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioRolRepository userInRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Optional<Usuario> optionalUser = usuarioRepository.findByUsername(username);

		if (optionalUser.isPresent()) {
			final Usuario usuario = optionalUser.get();
			final List<UsuarioRol> listaUsuarioRol = userInRoleRepository.findByUsername(usuario.getUsername());
			final String[] roles = listaUsuarioRol.stream().map(usuarioRol -> usuarioRol.getRol().getNombre())
					.toArray(String[]::new);
			return User.withUsername(usuario.getUsername()).password(usuario.getPassword()).roles(roles).build();
		}

		throw new UsernameNotFoundException(String.format("{security.usuario.no.encontrado}", username));
	}
}
