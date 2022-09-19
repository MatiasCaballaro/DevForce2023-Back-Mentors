package com.devForce.learning.service;

import com.devForce.learning.model.dto.SolicitudDTO;
import com.devForce.learning.model.entity.Solicitud;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface SolicitudService {

    public ResponseEntity<?> crearSolicitud(Solicitud solicitud, Authentication authentication);




    public ResponseEntity<?> error(String mensaje);


    public ResponseEntity<?> getTiposDeSolicitud();

    public ResponseEntity<?> getAreasDeSolicitud();

    /*
    public ResponseEntity<?> devolverSolicitudesUsuario(Usuario usuario);

    public ResponseEntity<?> devolverSolicitudesMentorAdmin(Usuario usuario);

    public ResponseEntity<?> getSolicitudesByIdUsuario(Long idUsuario);
     */

    public SolicitudDTO crearSolicitudDTO(Solicitud solicitud);
}
