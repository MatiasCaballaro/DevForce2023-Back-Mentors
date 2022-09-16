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

    // TODO: Solicitudes para Mentor
    // TODO: Solicitudes para Admin
    // TODO: Agregar PreAuthorize
    @GetMapping("/solicitudes")
    public List<Solicitud> allSolicitudes() {
        return new ArrayList<>(solicitudRepository.findAll());
    }

    @GetMapping("/solicitudesFiltradas")
    public ResponseEntity<?> userSolicitudes(@RequestParam Long idUsuario) {
        return solicitudService.getSolicitudesByIdUsuario(idUsuario);
    }


    @PostMapping("/nuevaSolicitud")
    public ResponseEntity<?> nuevaSolicitud (@RequestBody Solicitud solicitud){
        return solicitudService.crearSolicitud(solicitud);
    }

}
