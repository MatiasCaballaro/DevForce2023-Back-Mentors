package com.devForce.learning.service;

import com.devForce.learning.model.entity.Licencia;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface AdminService {

    public ResponseEntity<?> crearUsuario(Usuario usuario);

    public void addUsuario(Usuario usuario);

    public ResponseEntity<?> asignarLicencia(Solicitud solicitud);

    public List<Licencia> getLicencias ();

}
