package com.devForce.learning.controller;

import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.dto.request.LoginRequest;
import com.devForce.learning.model.dto.request.RegistroDTO;
import com.devForce.learning.model.entity.Licencia;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.LicenciaRepository;
import com.devForce.learning.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    LicenciaRepository licenciaRepository;

    @Autowired
    AdminService adminservice;


    @PostMapping("/registrarUsuario")
    public ResponseEntity<RespuestaDTO> authenticateUser(@Valid @RequestBody RegistroDTO registroDTO) {
        return adminservice.crearUsuario(registroDTO);
    }

    @PostMapping("/asignarLicencia")
    public ResponseEntity<?> licenciaAsignadaAUsuario(@RequestBody Solicitud solicitud) throws Exception {
        return adminservice.asignarLicencia(solicitud);
    }

    @GetMapping("/licencias")
    public List<Licencia> getLicencias() {
        return adminservice.getLicencias();
    }






}
