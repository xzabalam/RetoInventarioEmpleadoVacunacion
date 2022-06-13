package com.krugger.web.aop;

import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.krugger.data.entities.Empleado;
import com.krugger.data.enums.EstadoVacunacionEnum;

/**
 * @author xzabalam
 *
 */
@Aspect
@Component
public class UpdateEmpleadoAspect {

	/**
	 * Permite verificar que si se selecciono el estado de vacunaci[on como
	 * VACUNADO, en el json vengan los datos del tipo de vacuna, el n[umero de la
	 * dosis y la fecha en la que se vacuno. Caso contrario no se debe permitir
	 * actualizar el registro.
	 *
	 */
	@Before("execution(* com.krugger.web.controllers.EmpleadoController.updateEmpleado(.., @org.springframework.web.bind.annotation.RequestBody (*), ..))")
	public void before(JoinPoint joinPoint) {
		final Object[] args = joinPoint.getArgs();
		for (final Object arg : args) {
			if (arg instanceof Empleado) {
				final Empleado empleado = (Empleado) arg;
				if (EstadoVacunacionEnum.VACUNADO.equals(empleado.getEstadoVacunado())
						&& Objects.isNull(empleado.getTipoVacuna())) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe seleccionar el tipo de vacuna.");
				}

				if (Objects.isNull(empleado.getNumeroDosis()) || empleado.getNumeroDosis() < 1) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"El número de la dosis debe ser mayor a cero.");
				}

				if (Objects.isNull(empleado.getFechaVacunacion())) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Debe ingresar la fecha en la que le administraron la vacuna.");
				}
			}
		}
	}
}
