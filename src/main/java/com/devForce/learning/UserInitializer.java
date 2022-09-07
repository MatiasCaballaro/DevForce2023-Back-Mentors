package com.devForce.learning;

import com.devForce.learning.model.dto.UsuarioDTO;
import com.devForce.learning.model.entity.Licencia;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.LicenciaRepository;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
public class UserInitializer implements CommandLineRunner {

    @Value("${sample.data}")
    private Boolean datosDePrueba;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private LicenciaRepository licenciaRepository;

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
                user.setRol("Usuario");
                user.setHasTeams(faker.random().nextBoolean());
                System.out.println(user.toString());

                usuarioRepository.save(user);
            }

            /* INDIVIDUAL TEST ADMIN*/

            Usuario adminUser = new Usuario();
            adminUser.setNombre("Adrian");
            adminUser.setApellido("Pierro");
            adminUser.setUsername("AdrianPierro");
            adminUser.setMail((adminUser.getNombre()+"."+adminUser.getApellido()+"@gire.com").toLowerCase());
            adminUser.setPassword(adminUser.getNombre()+"123");
            adminUser.setPhone("123456789");
            adminUser.setRol("Admin");
            adminUser.setHasTeams(true);
            System.out.println(adminUser.toString());
            usuarioRepository.save(adminUser);

            /* INDIVIDUAL TEST ADMIN*/

            Usuario mentorUser = new Usuario();
            mentorUser.setNombre("Javier");
            mentorUser.setApellido("Ottina");
            mentorUser.setUsername("JavierOttina");
            mentorUser.setMail((mentorUser.getNombre()+"."+mentorUser.getApellido()+"@gire.com").toLowerCase());
            mentorUser.setPassword(mentorUser.getNombre()+"123");
            mentorUser.setPhone("123456789");
            mentorUser.setRol("Mentor");
            mentorUser.setHasTeams(true);
            System.out.println(mentorUser.toString());
            usuarioRepository.save(mentorUser);


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

                solicitud.setId(j);
                solicitud.setTipo("Udemy");
                solicitud.setDescripcion(faker.chuckNorris().fact());
                //solicitud.setApruebaMentorID();
                //solicitud.setApruebaAdminID();

                solicitud.setEstado("ACEPTADO");
                solicitud.setUsuario(usuarioRepository.findAll().stream().findAny().orElse(null));
                System.out.println(solicitud.toString());

                solicitudRepository.save(solicitud);
            }


            /* INDIVIDUAL TEST SOLICITUD*/

            /*Solicitud solicitud = new Solicitud();
            solicitud.setSolicitudId(1L);
            solicitud.setTipo("Udemy");
            solicitud.setEstado("pendienteAdmin");
            solicitud.setDescripcion(faker.chuckNorris().fact());
            //solicitud.setUsuario(usuarioRepository.findAll().stream().findAny().orElse(null));
            System.out.println(solicitud.toString());
            solicitudRepository.save(solicitud);*/




            /* CREACION DE LICENCIAS */

            System.out.println("---------- LICENCIAS ----------");



            for (int k = 1; k <11; k++) {
                List<Solicitud> list = null;
                Licencia licencia = new Licencia();

                licencia.setId(k);
                licencia.setSerial(faker.bothify("????##?###???###"));
                licencia.setSolicitudes(list);
                licencia.setOccupation("asignada");
                licencia.setExpdate(LocalDateTime.now().plusWeeks(3));
                licencia.setPlatform("Udemy");
                licencia.setSolicitudes(new ArrayList<>());

                System.out.println(licencia.toString());

                licenciaRepository.save(licencia);
            }

            Licencia licenciaPrueba= licenciaRepository.findById(1L);
            System.out.println("licenciaPrueba = " + licenciaPrueba);

            List<Solicitud> list = new ArrayList<>();
            list.add(solicitudRepository.findById(1L));
            licenciaPrueba.setSolicitudes(list);
            licenciaRepository.save(licenciaPrueba);

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
