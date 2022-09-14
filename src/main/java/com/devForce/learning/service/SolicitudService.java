package com.devForce.learning.service;

import com.devForce.learning.model.dto.SolicitudDTO;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import org.springframework.http.ResponseEntity;

public interface SolicitudService {

    public ResponseEntity<?> crearSolicitud(Usuario usuario, Solicitud solicitud);

    public ResponseEntity<?> devolverSolicitudesUsuario(Usuario usuario);

    public ResponseEntity<?> devolverSolicitudesMentorAdmin(Usuario usuario);

    public ResponseEntity<?> error(String mensaje);

    public ResponseEntity<?> getTiposDeSolicitud();

    public ResponseEntity<?> getAreasDeSolicitud();

    public ResponseEntity<?> getSolicitudesByIdUsuario(Long idUsuario);

    public SolicitudDTO crearSolicitudDTO(Solicitud solicitud);
}
