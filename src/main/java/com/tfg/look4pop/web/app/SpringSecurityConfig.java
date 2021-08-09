package com.tfg.look4pop.web.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.tfg.look4pop.web.app.auth.handler.LoginSuccessHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private DataSource dataSource; // Datasource para la conexion a la base de datos
	
	// Adaptador donde se van a guardar / registrar los usuarios
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		// Autenticacion 'EN MEMORIA'
		
		/*
		PasswordEncoder encoder = this.passwordEncoder;
		
		// Forma de encriptado de contraseñas
		UserBuilder users = User.builder().passwordEncoder(password -> {
			return encoder.encode(password);
		});
		
		// Creacion de usuarios, contraseñas, roles
		builder.inMemoryAuthentication()
		.withUser(users.username("admin").password("8080").roles("ADMIN")); // Password sin encriptar. Rol de administrador
															  				// Cuando se invoca este metodo, automaticamente el user builder va a codificar la contraseña
		*/
		
		
		// Autenticacion usando JDBC. Conexion a bbdd MySQL)
		builder.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(passwordEncoder)
			.usersByUsernameQuery("select username, password, enabled from users where username=?")
			.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");
		
		
	}

	// Gestion de autorizaciones hacia las rutas, recursos, ...
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf().disable()
		.authorizeRequests().antMatchers("/", "/index", "/consulta/**", "/locale/**", "/css/**", "/dwr/**", "/images/**", "/js/**").permitAll() // Permiso hacia rutas / recursos publicos
		.antMatchers("/admin/**").hasAnyRole("ADMIN").anyRequest().authenticated() // Autenticacion requerida
		.and()
			.formLogin() // Mostramos el formulario de login
				.successHandler(successHandler) // Pasamos la instancia del 'manejador de login satisfactorio'
				.loginPage("/login")  // 'loginPage("/login")' -> Mostramos el formulario personalizado (@GetMapping("/login") de LoginController)
			.permitAll() // Se muestra para todos los usuarios
		.and()
		.logout().permitAll() // Se incluye la funcionalidad de logout para todos los usuarios
		.and()
		.exceptionHandling().accessDeniedPage("/error_403"); // Misma ruta que la definida en la clase 'MvcConfig'
		
		// Cuando no se tienen permisos, se redirege automaticamente al formulario de login que provee Spring Security (http://localhost:8080/login)
		
	}
	
}
