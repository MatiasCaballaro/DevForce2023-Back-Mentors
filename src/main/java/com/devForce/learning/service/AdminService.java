package com.devForce.learning.service;

import com.devForce.learning.model.entity.Licencia;
import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.dto.request.RegistroDTO;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface AdminService {

    public ResponseEntity<RespuestaDTO> crearUsuario(RegistroDTO registroDTO);

    public void addUsuario(Usuario usuario);

    public ResponseEntity<?> asignarLicencia(Solicitud solicitud, Authentication authentication);

    public List<Licencia> getLicencias ();

}
