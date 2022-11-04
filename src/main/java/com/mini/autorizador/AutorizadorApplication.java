package com.mini.autorizador;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Log4j2
@SpringBootApplication
@ComponentScan("com.mini.autorizador.service")
@ComponentScan("com.mini.autorizador.controller")
@ComponentScan("com.mini.autorizador.exception")
public class AutorizadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutorizadorApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(Environment environment) {
		return args -> {
			log.info("our database URL connection will be " + environment.getProperty("spring.datasource.url"));
		};
	}

}
