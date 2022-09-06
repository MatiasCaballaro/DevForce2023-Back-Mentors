package com.devForce.learning.controller;

import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
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


    /*
    @GetMapping("/usuario")
    public ResponseEntity<Usuario> findByNombreandApellido (@RequestParam String nombre, @RequestParam String apellido) throws Exception {
        Usuario user= usuarioRepository.findByNombreAndApellido(nombre, apellido);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    */
    @GetMapping("/usuario")
    public ResponseEntity<Usuario> findById (@RequestParam Long id) throws Exception {
        Usuario user = usuarioRepository.getReferenceById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/usuario/solicitudes")
    public List<Solicitud> getSolicitudesUsuario (@RequestBody Usuario usuario) throws Exception {
        return usuario.getSolicitudes();
    }




}
