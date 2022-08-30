package com.devForce.learning.controller;

import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SolicitudController {

    @Autowired
    SolicitudRepository solicitudRepository;

    @GetMapping("/solicitudes")
    public List<Solicitud> allSolicitudes() {
        return solicitudRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @PostMapping("/nuevaSolicitud")
    public ResponseEntity<String> nuevaSolicitud (@RequestBody Solicitud solicitud, Usuario usuario){
        //TODO: Hacer la nuevaSolicitud. Tiene que validar que el usuario sea el logueado, y armar una nueva solicitud en estado "PENDIENTE-MENTOR"
        return new ResponseEntity<String>("Solicitud creada",HttpStatus.CREATED);
    }

}
