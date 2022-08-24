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
	public CommandLineRunner initData(UsuarioRepository usuarioRepository, SolicitudRepository solicitudRepository) {

		System.out.println("Hola crack, llegaste");

		return (args) -> {

			log.info("Starting to initialize sample data...");
			Faker faker = new Faker();


			/* Sample usuarios */

			System.out.println("---------- USUARIOS ----------");
			for (int i = 1; i < 11; i++) {

				Usuario user = new Usuario();

				user.setId(i);
				user.setNombre(faker.name().firstName());
				user.setApellido(faker.name().lastName());
				user.setUsername(user.getNombre() + user.getApellido());
				user.setMail(user.getNombre() + "." + user.getApellido() + "@gire.com");
				user.setPassword(user.getNombre() + "123");
				user.setPhone(faker.phoneNumber().cellPhone());
				user.setRol("User");
				user.setHasTeams(faker.random().nextBoolean());
				System.out.println(user);

				usuarioRepository.save(user);
			}


			/* Sample usuarioDTO */

			System.out.println("---------- USUARIO DTO ----------");

			Usuario usuarioParaPruebaDTO = usuarioRepository.findAll().stream().findFirst().orElse(null);
			System.out.println(usuarioParaPruebaDTO.toString());

			UsuarioDTO usuarioDTO = crearUsuarioDTO(usuarioParaPruebaDTO);

			System.out.println(usuarioDTO);


			/* Sample Solicitudes */

			System.out.println("---------- SOLICITUDES ----------");


			for (int j = 1; j < 11; j++) {

				Solicitud solicitud = new Solicitud();

				solicitud.setSolicitudId(j);
				solicitud.setTipo("Udemy");
				solicitud.setDescripcion(faker.chuckNorris().fact());
				//solicitud.setApruebaMentorID();
				//solicitud.setApruebaAdminID();
				solicitud.setEstado("pendienteAdmin");
				solicitud.setUsuario(usuarioRepository.findAll().stream().findAny().orElse(null));
				System.out.println(solicitud);

				solicitudRepository.save(solicitud);
			}


			log.info("Finished with data initialization");
		};


	}

	public UsuarioDTO crearUsuarioDTO(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(usuario.getId());
		dto.setNombre(usuario.getNombre());
		dto.setApellido(usuario.getApellido());
		dto.setUsername(usuario.getUsername());
		dto.setMail(usuario.getMail());
		dto.setPhone(usuario.getPhone());
		dto.setRol(usuario.getRol());
		dto.setHasTeams(usuario.getHasTeams());
		//dto.setSolicitudes(usuario.getSolicitudes());

		return dto;
	}
}

