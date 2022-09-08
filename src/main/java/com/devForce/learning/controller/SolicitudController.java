package com.devForce.learning.controller;

import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SolicitudController {

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/solicitudes")
    public List<Solicitud> allSolicitudes() {
        return solicitudRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/solicitudesFiltradas")
    public List<Solicitud> userSolicitudes(@RequestParam Long idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

        if(usuario != null) {

            if (usuario.getRol().equals("USUARIO"))
                return usuario.getSolicitudes();
            else
                return solicitudRepository.findAll();

        } else {
            return null;
        }




    }

    @PostMapping("/nuevaSolicitud")
    public ResponseEntity<String> nuevaSolicitud (@RequestBody Solicitud solicitud, Usuario usuario){
        //TODO: Hacer la nuevaSolicitud. Tiene que validar que el usuario sea el logueado, y armar una nueva solicitud en estado "PENDIENTE-MENTOR"
        return new ResponseEntity<String>("Solicitud creada",HttpStatus.CREATED);
    }

}
