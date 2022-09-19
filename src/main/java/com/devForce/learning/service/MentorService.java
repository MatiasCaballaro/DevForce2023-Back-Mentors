package com.devForce.learning.service;

import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.entity.Solicitud;
import org.springframework.http.ResponseEntity;

public interface MentorService {

    public ResponseEntity<RespuestaDTO> aceptarSolicitudPlataforma (Solicitud solicitud, int dias);

    public ResponseEntity<RespuestaDTO> rechazarSolicitud (Solicitud solicitud);

    public ResponseEntity<RespuestaDTO> devolverSolicitud (Solicitud solicitud);

    public ResponseEntity<RespuestaDTO> aceptarSolicitudSimple (Solicitud solicitud);

    public ResponseEntity<RespuestaDTO> aceptarSolicitud (Solicitud solicitud, Integer dias);



    }
