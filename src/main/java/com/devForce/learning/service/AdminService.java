package com.devForce.learning.service;

import com.devForce.learning.model.entity.Licencia;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import org.springframework.http.ResponseEntity;


public interface AdminService {

    public ResponseEntity<String> crearUsuario(Usuario usuario);

    public void addUsuario(Usuario usuario);

    public ResponseEntity<?> asignarLicencia(Solicitud solicitud, Licencia licencia);

}
