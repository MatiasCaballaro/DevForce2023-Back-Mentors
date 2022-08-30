package com.devForce.learning.service.impl;

import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public ResponseEntity<String> updateDatos(Usuario usuario) {
        Usuario usuarioYaExiste=usuarioRepository.findByNombreAndApellido(usuario.getNombre(), usuario.getApellido());
        //TODO: Cuando se realice la authentication, se tiene que validar que el usuario a actualizar coincide con el logueado
        return new ResponseEntity<String>("Datos Actualizado", HttpStatus.OK);
    }
}
