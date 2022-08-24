package com.devForce.learning;

import com.devForce.learning.model.dto.UsuarioDTO;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserInitializer implements CommandLineRunner {

    @Value("${sample.data}")
    private Boolean datosDePrueba;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;


    @Override
    public void run(String[] args) throws Exception {


        if(datosDePrueba){

            log.info("Starting to initialize sample data...");
            Faker faker = new Faker();


            /* CREACION DE USUARIOS */
            System.out.println("---------- USUARIOS ----------");
            for (int i = 1; i <11; i++) {

                Usuario user = new Usuario();

                user.setId(i);
                user.setNombre(faker.name().firstName());
                user.setApellido(faker.name().lastName());
                user.setUsername(user.getNombre()+user.getApellido());
                user.setMail(user.getNombre()+"."+user.getApellido()+"@gire.com");
                user.setPassword(user.getNombre()+"123");
                user.setPhone(faker.phoneNumber().cellPhone());
                user.setRol("User");
                user.setHasTeams(faker.random().nextBoolean());
                System.out.println(user.toString());

                usuarioRepository.save(user);
            }


            /* INDIVIDUAL TEST */
            Usuario user = new Usuario();
            user.setNombre("Matias");
            user.setApellido("Caballaro");
            user.setUsername("Caba87");
            user.setMail((user.getNombre()+"."+user.getApellido()+"@gire.com").toLowerCase());
            user.setPassword(user.getNombre()+"123");
            user.setPhone("123456789");
            user.setRol("User");
            user.setHasTeams(true);
            System.out.println(user.toString());
            usuarioRepository.save(user);


            /* Sample usuarioDTO */

            System.out.println("---------- USUARIO DTO ----------");

            Usuario usuarioParaPruebaDTO = usuarioRepository.findAll().stream().findFirst().orElse(null);
            System.out.println(usuarioParaPruebaDTO.toString());

            UsuarioDTO usuarioDTO = crearUsuarioDTO(usuarioParaPruebaDTO);

            System.out.println(usuarioDTO);


            /* CREACION DE SOLICITUDES */
            System.out.println("---------- SOLICITUDES ----------");

            for (int j = 1; j <11; j++) {

                Solicitud solicitud = new Solicitud();

                solicitud.setSolicitudId(j);
                solicitud.setTipo("Udemy");
                solicitud.setDescripcion(faker.chuckNorris().fact());
                //solicitud.setApruebaMentorID();
                //solicitud.setApruebaAdminID();
                solicitud.setEstado("pendienteAdmin");
                //solicitud.setUsuario(usuarioRepository.findAll().stream().findAny().orElse(null));
                System.out.println(solicitud.toString());

                solicitudRepository.save(solicitud);
            }



            /* INDIVIDUAL TEST SOLICITUD*/
            Solicitud solicitud = new Solicitud();
            solicitud.setSolicitudId(1L);
            solicitud.setTipo("Udemy");
            solicitud.setEstado("pendienteAdmin");
            solicitud.setDescripcion(faker.chuckNorris().fact());
            //solicitud.setUsuario(usuarioRepository.findAll().stream().findAny().orElse(null));
            System.out.println(solicitud.toString());
            solicitudRepository.save(solicitud);



            log.info("Finished with data initialization");

        }
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
