package com.devForce.learning.controller;

import com.devForce.learning.model.entity.Licencia;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.LicenciaRepository;
import com.devForce.learning.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    LicenciaRepository licenciaRepository;

    @Autowired
    AdminService adminservice;


    @PostMapping("/crearUsuario")
    public ResponseEntity<?> nuevoUsuario(@RequestBody Usuario usuario) throws  Exception {
        return adminservice.crearUsuario(usuario);
    }

    @PostMapping("/asignarLicencia")
    public ResponseEntity<?> licenciaAsignadaAUsuario(@RequestBody Solicitud solicitud) throws Exception {
        return adminservice.asignarLicencia(solicitud);
    }

    @GetMapping("/licencias")
    public List<Licencia> getLicencias() {
        return new ArrayList<>(licenciaRepository.findAll());
    }






}
