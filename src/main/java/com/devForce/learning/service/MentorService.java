package com.devForce.learning.service;

import com.devForce.learning.model.entity.Solicitud;
import org.springframework.http.ResponseEntity;

public interface MentorService {

    public ResponseEntity<?> aceptarSolicitudPlataforma (Solicitud solicitud, int dias);

    public ResponseEntity<?> rechazarSolicitud (Solicitud solicitud);

    public ResponseEntity<?> devolverSolicitud (Solicitud solicitud);

    public ResponseEntity<?> aceptarSolicitudSimple (Solicitud solicitud);



    }
