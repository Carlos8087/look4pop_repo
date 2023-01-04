package com.tfg.look4pop.web.app;

import java.util.Locale;

import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	/** INICIO Configuracion multilenguaje **/
	
	// Resuelve el Locale. Se guarda en la sesion
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		// Locale por defecto (codigo del lenguaje y pais). Idioma español -> es_ES
		localeResolver.setDefaultLocale(new Locale("es", "ES"));
		
		return localeResolver;
	}
	
	// Interceptor para cambiar el idioma
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		// Parametro para cambiar el idioma (pasado por URL)
		localeInterceptor.setParamName("lang");
		
		return localeInterceptor;
	}

	// Registro del interceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	/** FIN Configuracion multilenguaje **/
	
    // Configuracion DWR
	@Bean
    public ServletRegistrationBean<DwrSpringServlet> servletRegistrationBean() {
	       
		DwrSpringServlet servlet = new DwrSpringServlet();
	    ServletRegistrationBean<DwrSpringServlet> registrationBean = new ServletRegistrationBean<DwrSpringServlet>(servlet, "/dwr/*");
                 
        // Enable DWR to debug and enter the test page
        registrationBean.addInitParameter("debug", "true");
                 
        // Increase the loadability of the server, although DWR has a mechanism to protect the server from overload
        registrationBean.addInitParameter("pollAndCometEnabled", "true");
        
        // Asynchronously send data from a web-server to a browser
        registrationBean.addInitParameter("activeReverseAjaxEnabled", "true");
        
        return registrationBean;
    }
	
	// Para encriptar las contraseñas. Spring Security
	@Bean // Para registrarlo en el contenedor de Spring
	// PasswordEncoder que va a utilizar por defecto Spring Security
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Para registrar un controlador de vista (se encargan unicamente de cargar una vista. No tienen logica como tal)
	// El metodo tiene que llamarse obligatoriamente 'addViewControllers'
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403"); // Ruta URL del 'RequestMapping' + la vista que queremos cargar (error_403.html)
	}

}
