package com.devForce.learning.service;

import com.devForce.learning.model.dto.UsuarioDTO;
import com.devForce.learning.model.entity.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsuarioService {

    //Actualiza los datos de un usuario ya creado
    public ResponseEntity<String> updateDatos (Usuario usuario);

    //Crea un UsuarioDTO a partir de un Usuario
    public UsuarioDTO crearUsuarioDTO(Usuario usuario);

    //Devuelve una lista de <Usuario> como <UsuarioDTO>
    public List<UsuarioDTO> allUsersDTO();

}
