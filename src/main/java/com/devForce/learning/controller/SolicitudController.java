package com.devForce.learning.controller;

import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SolicitudController {

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SolicitudService solicitudService;

    @GetMapping("/solicitudes")
    public List<Solicitud> allSolicitudes() {
        return new ArrayList<>(solicitudRepository.findAll());
    }

    // TODO Hacer un método para esta lógica en Solicitud Service y que este nuevo método llame a los otros dos
    // Es necesario que las devoluciones de búsqueda pasen por la respuestaDTO??
    @GetMapping("/solicitudesFiltradas")
    public ResponseEntity<?> userSolicitudes(@RequestParam Long idUsuario) {
        return solicitudService.getSolicitudesByIdUsuario(idUsuario);
    }

    //TODO: No se hizo este método
    @PostMapping("/nuevaSolicitud")
    public ResponseEntity<String> nuevaSolicitud (@RequestBody Solicitud solicitud, Usuario usuario){
        //TODO: Hacer la nuevaSolicitud. Tiene que validar que el usuario sea el logueado, y armar una nueva solicitud en estado "PENDIENTE-MENTOR"
        return new ResponseEntity<String>("Solicitud creada",HttpStatus.CREATED);
    }

}
