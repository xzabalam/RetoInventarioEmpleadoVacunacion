package com.krugger.config.anotations.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.krugger.config.anotations.CedulaValida;

/**
 * @author xzabalam
 *
 */
public class CedulaConstraintValidator implements ConstraintValidator<CedulaValida, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!value.matches("[0-9]+")) {
			return false;
		}

		final String dosPrimerosDigitosDeLaCedula = value.substring(0, 2);
		final int digitosDeProvincia = Integer.parseInt(dosPrimerosDigitosDeLaCedula);

		// Si no esta en el rango de las provincias existentes en el pais, la cedula no
		// sera valida
		if (digitosDeProvincia >= 1 && digitosDeProvincia <= 24) {
			return true;
		}

		return false;
	}
}
