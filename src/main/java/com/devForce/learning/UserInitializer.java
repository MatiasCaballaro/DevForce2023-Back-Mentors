package com.devForce.learning;

import com.devForce.learning.model.dto.UsuarioDTO;
import com.devForce.learning.model.entity.*;
import com.devForce.learning.repository.LicenciaRepository;
import com.devForce.learning.repository.RoleRepository;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.service.UsuarioService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String[] args) throws Exception {

        if(datosDePrueba){
            Role userRole = new Role(ERole.ROLE_USUARIO);
            Role mentorRole = new Role(ERole.ROLE_MENTOR);
            Role adminRole = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(userRole);
            roleRepository.save(mentorRole);
            roleRepository.save(adminRole);

            Set<Role> userRoles = new HashSet();
            Role r = roleRepository.findByName(ERole.ROLE_USUARIO).orElse(null);
            userRoles.add(r);

            Set<Role> mentorRoles = new HashSet();
            Role m = roleRepository.findByName(ERole.ROLE_MENTOR).orElse(null);
            mentorRoles.add(m);

            Set<Role> adminRoles = new HashSet();
            Role a = roleRepository.findByName(ERole.ROLE_ADMIN).orElse(null);
            adminRoles.add(a);

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
                user.setEmail(user.getNombre()+"."+user.getApellido()+"@gire.com");
                user.setPassword(encoder.encode(user.getNombre()+"123"));
                user.setPhone(faker.phoneNumber().cellPhone());
                user.setHasTeams(faker.random().nextBoolean());
                user.setRoles(userRoles);
                System.out.println(user.toString());

                usuarioRepository.save(user);
            }

            /* INDIVIDUAL TEST MENTOR*/

            Usuario userUser = new Usuario();
            userUser.setId(11);
            userUser.setNombre("Nicolas");
            userUser.setApellido("Rivas");
            userUser.setUsername("NicolasRivas");
            userUser.setEmail((userUser.getNombre()+"."+userUser.getApellido()+"@gire.com").toLowerCase());
            userUser.setPassword(encoder.encode(userUser.getNombre()+"123"));
            userUser.setPhone("123456789");
            userUser.setHasTeams(true);
            userUser.setRoles(userRoles);
            System.out.println(userUser.toString());
            usuarioRepository.save(userUser);

            /* INDIVIDUAL TEST MENTOR*/

            Usuario mentorUser = new Usuario();
            mentorUser.setId(12);
            mentorUser.setNombre("Javier");
            mentorUser.setApellido("Ottina");
            mentorUser.setUsername("JavierOttina");
            mentorUser.setEmail((mentorUser.getNombre()+"."+mentorUser.getApellido()+"@gire.com").toLowerCase());
            mentorUser.setPassword(encoder.encode(mentorUser.getNombre()+"123"));
            mentorUser.setPhone("123456789");
            mentorUser.setHasTeams(true);
            mentorUser.setRoles(mentorRoles);
            mentorUser.setMentorArea("BACKEND");
            System.out.println(mentorUser.toString());
            usuarioRepository.save(mentorUser);

            /* INDIVIDUAL TEST ADMIN*/

            Usuario adminUser = new Usuario();
            adminUser.setId(13);
            adminUser.setNombre("Adrian");
            adminUser.setApellido("Pierro");
            adminUser.setUsername("AdrianPierro");
            adminUser.setEmail((adminUser.getNombre()+"."+adminUser.getApellido()+"@gire.com").toLowerCase());
            adminUser.setPassword(encoder.encode(adminUser.getNombre()+"123"));
            adminUser.setPhone("123456789");
            adminUser.setHasTeams(true);
            adminUser.setRoles(adminRoles);
            System.out.println(adminUser.toString());
            usuarioRepository.save(adminUser);




            /* Sample usuarioDTO */

            System.out.println("---------- USUARIO DTO ----------");

            Usuario usuarioParaPruebaDTO = usuarioRepository.findAll().stream().findFirst().orElse(null);
            System.out.println(usuarioParaPruebaDTO.toString());
            UsuarioDTO usuarioDTO = usuarioService.crearUsuarioDTO(usuarioParaPruebaDTO);
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
                //solicitud.setTiempoSolicitado(45);
                solicitud.setEstado("PENDIENTE-MENTOR");
                solicitud.setArea("BACKEND");
                //solicitud.setUsuario(usuarioRepository.findAll().stream().findAny().orElse(null));
                solicitud.setUsuario(usuarioRepository.findById(11));
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
                licencia.setSerie(faker.bothify("????##?###???###"));
                licencia.setSolicitudes(list);
                licencia.setEstado("DISPONIBLE");
                licencia.setPlataforma("Udemy");
                licencia.setSolicitudes(new ArrayList<>());

                System.out.println(licencia.toString());

                licenciaRepository.save(licencia);
            }

            Licencia licenciaPrueba= licenciaRepository.findById(1L);
            System.out.println("licenciaPrueba = " + licenciaPrueba);

            log.info("Finished with data initialization");

        }
    }


}
