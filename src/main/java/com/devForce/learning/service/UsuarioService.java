package com.devForce.learning.service;

import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.dto.UsuarioDTO;
import com.devForce.learning.model.dto.authRequestDTO.LoginRequest;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface UsuarioService {

    //Actualiza los datos de un usuario ya creado
    public ResponseEntity<RespuestaDTO> updateDatos (Usuario usuario);

    //Crea un UsuarioDTO a partir de un Usuario
    public UsuarioDTO crearUsuarioDTO(Usuario usuario);

    public UserDetailsImpl obtenerUsuario();

    public ResponseEntity<RespuestaDTO> login(@Valid @RequestBody LoginRequest loginRequest);




}
