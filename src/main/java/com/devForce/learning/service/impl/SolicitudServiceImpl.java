package com.devForce.learning.service.impl;

import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.service.SolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SolicitudServiceImpl implements SolicitudService {

    @Override
    public ResponseEntity<String> crearSolicitud(Usuario usuario, Solicitud solicitud) {
        if (usuario.getRol()=="Usuario"||usuario.getRol()=="Mentor"){
            return new ResponseEntity<String>("Solicitud creada", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<String>("No tienes permisos crack", HttpStatus.FORBIDDEN);
        }
    }

}
