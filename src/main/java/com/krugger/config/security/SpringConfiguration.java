package com.krugger.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.krugger.data.enums.AuthoritiesEnum;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringConfiguration implements WebMvcConfigurer {

	private static final String[] AUTH_WHITELIST = {
			// -- Swagger UI v2
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui.html", "/webjars/**",
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**", "/swagger-ui/**" };

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				// Configurar el acceso libre a swagger ui
				.antMatchers(AUTH_WHITELIST).permitAll()
				// Configurar el acceso al api rest para obtener empleados
				.antMatchers(HttpMethod.GET, "/empleados/{cedula}")
				.hasAnyAuthority(AuthoritiesEnum.ROLE_ADMINISTRADOR.name(), AuthoritiesEnum.ROLE_EMPLEADO.name())
				// Configurar el acceso a la actualizaci[on del empleado que lo puede hacer el
				// admin o el empleado
				.antMatchers(HttpMethod.PUT, "/empleados/{cedula}")
				.hasAnyAuthority(AuthoritiesEnum.ROLE_ADMINISTRADOR.name(), AuthoritiesEnum.ROLE_EMPLEADO.name())
				// Configurar el acceso al api rest para obtener el username de un empleado
				.antMatchers(HttpMethod.GET, "/usuarios/username/{cedula}")
				.hasAnyAuthority(AuthoritiesEnum.ROLE_ADMINISTRADOR.name(), AuthoritiesEnum.ROLE_EMPLEADO.name())
				// Configurar el acceso al api rest
				.antMatchers("/empleados/**").hasAuthority(AuthoritiesEnum.ROLE_ADMINISTRADOR.name())

				// El resto de solicitudes debe estar autenticadas
				.anyRequest().authenticated().and().httpBasic();

		return http.build();
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
}
