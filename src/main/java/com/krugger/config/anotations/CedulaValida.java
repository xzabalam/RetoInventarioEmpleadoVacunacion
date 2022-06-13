package com.krugger.config.anotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.krugger.config.anotations.impl.CedulaConstraintValidator;

/**
 * @author xzabalam
 *
 */
@Documented
@NotNull(message = "{entity.empleado.cedula.invalida}")
@NotEmpty(message = "{entity.empleado.cedula.invalida}")
@Digits(fraction = 0, integer = 10, message = "{entity.empleado.cedula.digitos}")
@Size(min = 10, max = 10, message = "{entity.empleado.size.cedula}")
@Constraint(validatedBy = CedulaConstraintValidator.class)
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CedulaValida {
	Class<?>[] groups() default {};

	String message() default "{entity.empleado.cedula.invalida}";

	Class<? extends Payload>[] payload() default {};

}
