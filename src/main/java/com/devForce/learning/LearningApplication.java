package com.devForce.learning;

import com.devForce.learning.model.dto.UsuarioDTO;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class LearningApplication {


	public static void main(String[] args) {
		SpringApplication.run(LearningApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData() {

		System.out.println("Hola crack, llegaste");

		return (args) -> {


		};
	}
}

