package com.krugger.web.controllers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krugger.business.services.UsuarioService;
import com.krugger.data.entities.Usuario;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService ususarioService;

	@GetMapping("/username/{cedula}")
	@Operation(summary = "Permite obtener el username de un empleado por el número de cédula.")
	public ResponseEntity<Map<String, String>> getUsernameByCedula(@PathVariable("cedula") String cedula) {
		final Usuario usuario = ususarioService.getUserByCedulaEmpleado(cedula);

		final Map<String, String> respuesta = new ConcurrentHashMap<>();
		respuesta.put("username", usuario.getUsername());

		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
}
