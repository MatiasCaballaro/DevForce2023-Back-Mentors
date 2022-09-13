package com.devForce.learning.controller;

import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mentor")
public class MentorController {

    @Autowired
    MentorService mentorService;


    //TODO hacer método para que el mentor pase la solicitud a "PENDIENTE MENTOR"
    // (tiene que pasar un int para los días, el cual tambien puede ser 0)
    // Tiene que devolver una respuesta DTO

    @PutMapping("/aceptarSolicitudPlataforma")
    public ResponseEntity<?> aceptarSolicitudPlataforma(@RequestBody Solicitud solicitud, @RequestParam(required = false) Integer dias) throws Exception {
        return mentorService.aceptarSolicitud(solicitud, dias);
    }

    @PutMapping("/rechazarSolicitudPlataforma")
    public ResponseEntity<?> rechazarSolicitudPlataforma(@RequestBody Solicitud solicitud) throws Exception {
        return mentorService.rechazarSolicitud(solicitud);
    }

    @PutMapping("/devolverSolicitudPlataforma")
    public ResponseEntity<?> devolverSolicitudPlataforma(@RequestBody Solicitud solicitud) throws Exception {
        return mentorService.devolverSolicitud(solicitud);
    }

    //TODO hacer método para que el mentor pase la solicitud a "DEVUELTO-USER"
    // Tiene que devolver una respuesta DTO
}
