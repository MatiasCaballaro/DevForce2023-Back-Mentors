package com.devForce.learning.service;

import com.devForce.learning.model.entity.Licencia;
import com.devForce.learning.model.entity.Usuario;
import org.springframework.http.ResponseEntity;


public interface AdminService {

    public ResponseEntity<String> crearUsuario(Usuario usuario);

    public void addUsuario(Usuario usuario);

    public void asignarLicencia(Usuario usuario, Licencia licencia);

}
