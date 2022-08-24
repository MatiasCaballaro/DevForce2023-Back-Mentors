package com.devForce.learning.controller;

import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class Prueba {

    @Autowired
    UsuarioRepository usuarioRepository;

    @RequestMapping("/usuarios")
    public List<Usuario> allUsers() {
        return usuarioRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }
}
