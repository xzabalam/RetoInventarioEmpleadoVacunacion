package com.krugger.config.anotations.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import com.krugger.config.anotations.PasswordValido;

/**
 * Valida un password con las siguientes reglas
 *
 * <ul>
 * <li>Minimo 8 caracteres</li>
 * <li>Maximo 20 caracteres</li>
 * <li>al menos una mayuscula</li>
 * <li>al menos una minuscula</li>
 * <li>al menos un digito</li>
 * <li>al menos un caracter especial</li>
 * <li>sin espacios en blanco</li>
 * </ul>
 *
 * @author xzabalam
 *
 */
public class PasswordConstraintValidator implements ConstraintValidator<PasswordValido, String> {

	private static final List<Rule> RULES = Arrays.asList(
			// al menos 8 caracteres y mmaximo 20 caracteres
			new LengthRule(8, 20),

			// al menos una mayuscula
			new CharacterRule(EnglishCharacterData.UpperCase, 1),

			// al menos una minuscula
			new CharacterRule(EnglishCharacterData.LowerCase, 1),

			// al menos un digito
			new CharacterRule(EnglishCharacterData.Digit, 1),

			// al menos un caracter especial
			new CharacterRule(EnglishCharacterData.Special, 1),

			// sin espacios en blanco
			new WhitespaceRule()

	);

	@Override
	public void initialize(PasswordValido validPassword) {
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		final PasswordValidator validator = new PasswordValidator(RULES);
		final RuleResult result = validator.validate(new PasswordData(password));

		if (result.isValid()) {
			return true;
		}

		final List<String> messages = validator.getMessages(result);
		final String messageTemplate = messages.stream().collect(Collectors.joining(","));
		context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation()
				.disableDefaultConstraintViolation();
		return false;
	}
}