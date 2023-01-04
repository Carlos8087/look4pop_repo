package com.tfg.look4pop.web.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@MapperScan(basePackages = "com.tfg.look4pop.web.app.models.mapper")
@ImportResource("classpath:dwr/config/dwr-config.xml")
public class SpringBootLook4popApplication implements CommandLineRunner {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootLook4popApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		String password = "8080";
		
		// Encriptado de la contraseña '8080'
		for (int i = 0; i < 2; i++) {
			String bcryptPassword = passwordEncoder.encode(password);
			System.out.println(bcryptPassword);
		}
		
	}

}
