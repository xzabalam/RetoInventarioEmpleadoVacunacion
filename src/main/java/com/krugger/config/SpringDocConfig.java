package com.krugger.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

	private static final String GRUPO = "controladores";
	private static final String REGLA_EMPLEADOS = "/empleados/**";
	private static final String REGLA_USUARIOS = "/usuarios/**";

	@Bean
	public GroupedOpenApi getDocket() {
		return GroupedOpenApi.builder().group(GRUPO).pathsToMatch(REGLA_EMPLEADOS, REGLA_USUARIOS).build();
	}
}
