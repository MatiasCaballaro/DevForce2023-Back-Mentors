package com.devForce.learning.service;

import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import org.springframework.http.ResponseEntity;

public interface SolicitudService {

    public ResponseEntity<String> crearSolicitud(Usuario usuario, Solicitud solicitud);
}
