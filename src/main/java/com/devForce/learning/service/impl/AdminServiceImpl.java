package com.devForce.learning.service.impl;

import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.entity.Licencia;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.LicenciaRepository;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    LicenciaRepository licenciaRepository;


    @Override
    //TODO Consultar con front cómo espera se espera un error de tipo USUARIO EXISTE O NO TIENE PERMISOS?
    public ResponseEntity<?> crearUsuario(Usuario usuario) {
        log.info("Intentando guardar usuario...");
        //TODO: Chequear que soy un usuario Admin
        Usuario usuarioYaExiste = usuarioRepository.findByNombreAndApellido(usuario.getNombre(), usuario.getApellido());
        if(usuarioYaExiste==null) {
            addUsuario(usuario);
            log.info("Usuario creado");
            return new ResponseEntity<>(usuarioRepository.findByNombreAndApellido(usuario.getNombre(), usuario.getApellido()), HttpStatus.CREATED);
        }
        else {
            log.info("Usuario no se ha podido crear - Duplicado");
            return new ResponseEntity<>("Usuario ya existe", HttpStatus.CONFLICT);
        }
    }

    @Override
    public void addUsuario(Usuario usuario) {
        Usuario newUsuario = new Usuario(
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsername(),
                usuario.getMail(),
                usuario.getPassword(),
                usuario.getPhone(),
                usuario.getRol(),
                usuario.getHasTeams()
        );
        usuarioRepository.save(newUsuario);
    }

    //TODO: Terminar asignarLicenciaMétodo
    @Override
    public ResponseEntity<?> asignarLicencia(Solicitud solicitud) {
        // TODO VERIFICAR que el usuario logueado sea admin

        // AGREGO ESTO PARA TESTING SOLAMENTE, DESPUES BORRAR
        solicitud.setEstado("PENDIENTE-ADMIN");

        System.out.println("El usuario de la solicitud es: " + solicitud.getUsuario());

        // Buscamos si el usuario ya tenía una licencia
        List<Solicitud> solicitudesAceptadas = solicitudRepository.findByUsuarioAndEstado(solicitud.getUsuario(), "PENDIENTE-ADMIN");

            for (Solicitud solicitudAux : solicitudesAceptadas){
                if (solicitudAux.getLicencia() != null){
                    // verificar que la licencia no esté fuera de plazo
                    if (!solicitudAux.getLicencia().getVencimiento().isBefore(LocalDateTime.now())) {
                       //TODO Nuevo método extender tiempo de la misma licencia
                    }
                }
            }

        //TODO: Asignar una licencia disponible
        Licencia licencia = licenciaRepository.findFirstByEstadoOrderById("DISPONIBLE");
        solicitud.setLicencia(licencia);
        solicitud.setEstado("ACEPTADA");
        solicitudRepository.save(solicitud);
        licencia.setEstado("ASIGNADA");
        licenciaRepository.save(licencia);
        solicitud=solicitudRepository.findById(solicitud.getId());

        // *************** PRUEBA RESPUESTA DTO ****************

        RespuestaDTO respuestaDTO = new RespuestaDTO();

        respuestaDTO.setContenido(solicitud);
        respuestaDTO.setOk(true);

        return new ResponseEntity<>(respuestaDTO, HttpStatus.OK);
    }

}
