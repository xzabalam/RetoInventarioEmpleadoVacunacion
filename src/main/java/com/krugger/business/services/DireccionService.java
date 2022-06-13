package com.krugger.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krugger.data.entities.Direccion;
import com.krugger.data.repositories.DireccionRepository;

/**
 * Servicio que permite obtener la data de las direcciones de los direcciones
 *
 * @author xzabalam
 *
 */
@Service
public class DireccionService {

	@Autowired
	private DireccionRepository direccionRepository;

	public List<Direccion> getDireccionesByEmpleado(Long idEmpleado) {
		return direccionRepository.findAllByEmpleado(idEmpleado);
	}
}
