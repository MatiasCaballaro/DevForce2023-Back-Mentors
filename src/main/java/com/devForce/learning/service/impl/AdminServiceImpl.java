package com.devForce.learning.service.impl;

import com.devForce.learning.model.entity.Licencia;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public ResponseEntity<String> crearUsuario(Usuario usuario) {
        //TODO: Chequear que soy un usuario Admin
        Usuario usuarioYaExiste=usuarioRepository.findByNombreAndApellido(usuario.getNombre(), usuario.getApellido());
        if(usuarioYaExiste==null){
            addUsuario(usuario);
            return new ResponseEntity<>("Usuario Creado", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Usuario ya existe", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void addUsuario(Usuario usuario) {
        Usuario newUsuario = new Usuario(
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsername(),
                usuario.getMail(),
                usuario.getPassword(),
                usuario.getPhone(),
                usuario.getRol(),
                usuario.getHasTeams()
        );
        usuarioRepository.save(newUsuario);
    }

    //TODO: Realizar el mètodo de asignar licencia
    @Override
    public void asignarLicencia(Usuario usuario, Licencia licencia) {
    }

}
