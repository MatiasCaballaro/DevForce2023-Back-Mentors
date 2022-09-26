package com.devForce.learning.service;

import com.devForce.learning.model.dto.SolicitudDTO;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface SolicitudService {

    public ResponseEntity<?> crearSolicitud(Solicitud solicitud, Authentication authentication);

    public ResponseEntity<?> getTiposDeSolicitud();

    public ResponseEntity<?> getAreasDeSolicitud();

    public List<SolicitudDTO> solicitudesUsuario();

    public List<SolicitudDTO> solicitudesMentor();

    public List<SolicitudDTO> solicitudesAdmin();

    public SolicitudDTO crearSolicitudDTO(Solicitud solicitud);
}
