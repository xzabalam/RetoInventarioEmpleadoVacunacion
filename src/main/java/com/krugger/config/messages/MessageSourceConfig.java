package com.krugger.config.messages;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Se configura la utilizaci[on de mensajes con internacionalizacion
 *
 * @author xzabalam
 *
 */
@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {

	private static final String BASENAME = "classpath:messages";

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Override
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		final LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Bean
	public LocaleResolver localeResolver() {
		final SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.forLanguageTag("es_EC"));
		return localeResolver;
	}

	@Bean
	public MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();

		resourceBundleMessageSource.setBasename(BASENAME);
		resourceBundleMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);

		return resourceBundleMessageSource;
	}

}
