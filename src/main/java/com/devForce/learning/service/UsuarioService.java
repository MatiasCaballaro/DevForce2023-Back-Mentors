package com.devForce.learning.service;

import com.devForce.learning.model.entity.Usuario;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {

    //Actualiza los datos de un usuario ya creado
    public ResponseEntity<String> updateDatos (Usuario usuario);


}
