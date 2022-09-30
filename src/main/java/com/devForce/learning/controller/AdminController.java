package com.devForce.learning.controller;

import com.devForce.learning.model.dto.LicenciaDTO;
import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.dto.authRequestDTO.RegistroDTO;
import com.devForce.learning.model.entity.Licencia;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminservice;

    @PostMapping("/registrarUsuario")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> authenticateUser(@Valid @RequestBody RegistroDTO registroDTO) {
        return adminservice.crearUsuario(registroDTO);
    }

    @PostMapping("/asignarLicencia")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> licenciaAsignadaAUsuario(@RequestBody Solicitud solicitud) throws Exception {
        return adminservice.asignarLicencia(solicitud);
    }


    @GetMapping("/licencias")
    //@PreAuthorize("hasRole('ADMIN')")
    public List<LicenciaDTO> getLicencias() {
        return adminservice.getLicencias();
    }


}
