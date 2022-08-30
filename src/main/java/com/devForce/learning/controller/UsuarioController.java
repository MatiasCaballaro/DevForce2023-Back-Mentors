package com.devForce.learning.controller;

import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @RequestMapping("/usuarios")
    public List<Usuario> allUsers() {
        return usuarioRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/usuario")
    public ResponseEntity<Usuario> findByNombreandApellido (@RequestParam String nombre, @RequestParam String apellido) throws Exception {
        Usuario user= usuarioRepository.findByNombreAndApellido(nombre, apellido);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
