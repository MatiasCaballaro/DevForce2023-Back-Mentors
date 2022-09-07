package com.devForce.learning.service.impl;

import com.devForce.learning.model.entity.Licencia;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.LicenciaRepository;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.service.AdminService;
import org.apache.logging.log4j.util.EnglishEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    LicenciaRepository licenciaRepository;


    @Override
    //TODO pasar String a ?
    public ResponseEntity<String> crearUsuario(Usuario usuario) {
        //TODO: Chequear que soy un usuario Admin
        Usuario usuarioYaExiste=usuarioRepository.findByNombreAndApellido(usuario.getNombre(), usuario.getApellido());
        if(usuarioYaExiste==null) {
            addUsuario(usuario);
            return new ResponseEntity<>("Usuario Creado", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Usuario ya existe", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> asignarLicencia(Solicitud solicitud, Licencia licencia) {
        // TODO VERIFICAR que el usuario logueado sea admin

        // AGREGO ESTO PARA TESTING SOLAMENTE, DESPUES BORRAR
        solicitud.setEstado("PENDIENTE-ADMIN");

        System.out.println("El usuario de la solicitud es: " + solicitud.getUsuario());

        // Buscamos si el usuario ya tenía una licencia
        List<Solicitud> solicitudesAceptadas = solicitudRepository.findByUsuarioAndEstado(solicitud.getUsuario(), "PENDIENTE-ADMIN");

            for (Solicitud solicitudAux : solicitudesAceptadas){
                if (solicitudAux.getLicencia() != null){
                    // verificar que la licencia no esté fuera de plazo
                    if (!licencia.getExpdate().isBefore(LocalDateTime.now())) {
                       //TODO Nuevo método extender tiempo de la misma licencia
                    }
                }
            }

        //TODO: Asignar una licencia disponible

        solicitud.setLicencia(licencia);
        solicitudRepository.save(solicitud);

        return new ResponseEntity<>(solicitud, HttpStatus.OK);

    }



}
