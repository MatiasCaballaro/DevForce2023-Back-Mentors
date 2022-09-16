package com.devForce.learning.controller;

import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.dto.UsuarioDTO;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;


    @RequestMapping("/usuarios")
    public List<Usuario> allUsers() {
        return usuarioRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @RequestMapping("/usuariosDTO")
    public List<UsuarioDTO> allUsersDTO() {
        return usuarioService.allUsersDTO();
    }


    @GetMapping("/usuario")
    public ResponseEntity<Usuario> findById (@RequestParam Long id) throws Exception {
        Usuario user = usuarioRepository.findById(id).orElse(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/usuario/solicitudes")
    public List<Solicitud> getSolicitudesUsuario (@RequestBody Usuario usuario) throws Exception {
        return usuario.getSolicitudes();
    }

    @PutMapping("/usuario/configuracion")
    public ResponseEntity<RespuestaDTO> updateDatos (@RequestBody Usuario usuario) throws Exception {
        return usuarioService.updateDatos(usuario);
    }




}
