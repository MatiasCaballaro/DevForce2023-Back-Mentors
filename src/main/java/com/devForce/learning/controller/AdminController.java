package com.devForce.learning.controller;

import com.devForce.learning.model.entity.Licencia;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.LicenciaRepository;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    LicenciaRepository licenciaRepository;

    @Autowired
    AdminService adminservice;


    @PostMapping("/crearUsuario")
    public ResponseEntity<String> nuevoUsuario(@RequestBody Usuario usuario) throws  Exception {
        return adminservice.crearUsuario(usuario);
    }

    //TODO: Averiguar como pasarle la licencia al método y hacerlo funcar. Agregar parametro cantidad de dias y preguntar si está bien lo del Serial
    @PostMapping("/asignarLicencia")
    public ResponseEntity<String> licenciaAsignadaAUsuario(@RequestBody Solicitud solicitud, @RequestParam String serial) throws Exception {
        return adminservice.asignarLicencia(solicitud, licenciaRepository.findBySerial(serial));
    }

    @GetMapping("/licencias")
    public List<Licencia> getLicencias() {
        return licenciaRepository.findAll()
                .stream()
                .collect(Collectors.toList());
    }






}
