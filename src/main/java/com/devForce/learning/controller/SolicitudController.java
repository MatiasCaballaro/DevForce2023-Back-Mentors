package com.devForce.learning.controller;

import com.devForce.learning.model.dto.SolicitudDTO;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.security.services.UserDetailsImpl;
import com.devForce.learning.service.SolicitudService;
import com.devForce.learning.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    SolicitudService solicitudService;

    @Autowired
    UsuarioService usuarioService;

    // TODO: Consultar con Front si las solicitudDTO les sirve. Sino hay que crear una clase DTO para cada solicitudFiltrada por tipo de Usuario


    // SOLO PARA TEST
    @GetMapping("/test/solicitudes")
    public List<Solicitud> allSolicitudes(){
        return new ArrayList<>(solicitudRepository.findAll());
    }

    // SOLO PARA TEST
    @GetMapping("/test/solicitudesDTO")
    public List<SolicitudDTO> allSolicitudesDTO() {
        return new ArrayList<SolicitudDTO>(solicitudRepository.findAll().stream().map(solicitudService::crearSolicitudDTO).collect(Collectors.toList()));
    }


    @GetMapping("/solicitudesusuario")
    @PreAuthorize("hasRole('USUARIO') or hasRole('MENTOR')")
    public List<SolicitudDTO> solicitudesUsuario(){
        UserDetailsImpl usuarioAuth = usuarioService.obtenerUsuario();
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioAuth.getId());
        return new ArrayList<SolicitudDTO>(solicitudRepository.findByUsuario(usuario.get())
                .stream().map(solicitudService::crearSolicitudDTO).collect(Collectors.toList()));
    }


    @GetMapping("/solicitudesmentor")
    @PreAuthorize("hasRole('MENTOR')")
    public List<SolicitudDTO> solicitudesMentor(){
        UserDetailsImpl usuarioAuth = usuarioService.obtenerUsuario();
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioAuth.getId());
        return new ArrayList<SolicitudDTO>(solicitudRepository.findByUsuarioNot(usuario.get())
                .stream().map(solicitudService::crearSolicitudDTO).collect(Collectors.toList()));
    }

    @GetMapping("/solicitudesadmin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SolicitudDTO> solicitudesAdmin() {
        return new ArrayList<SolicitudDTO>(solicitudRepository.findAll().stream().map(solicitudService::crearSolicitudDTO).collect(Collectors.toList()));
    }

/*
    @GetMapping("/solicitudesFiltradas")
    public ResponseEntity<?> userSolicitudes(@RequestParam Long idUsuario) {
        return solicitudService.getSolicitudesByIdUsuario(idUsuario);
    }
*/

    @PostMapping("/nuevaSolicitud")
    @PreAuthorize("hasRole('USUARIO') or hasRole('MENTOR')")
    public ResponseEntity<?> nuevaSolicitud (@RequestBody Solicitud solicitud, Authentication authentication){
        return solicitudService.crearSolicitud(solicitud, authentication);
    }

}
