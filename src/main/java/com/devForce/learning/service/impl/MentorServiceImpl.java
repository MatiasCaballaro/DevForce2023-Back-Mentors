package com.devForce.learning.service.impl;

import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.repository.LicenciaRepository;
import com.devForce.learning.repository.MentorRepository;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    LicenciaRepository licenciaRepository;

    @Autowired
    MentorRepository mentorRepository;

    public ResponseEntity<?> aceptarSolicitud (Solicitud solicitud, Integer dias) {
        // TODO: Cada vez que el mentor aprueba una solicitud setear el apruebaMentorID
        if (dias == null)
            return aceptarSolicitudSimple(solicitud);
        else
            return aceptarSolicitudPlataforma(solicitud, dias);
    }

    public ResponseEntity<?> aceptarSolicitudPlataforma (Solicitud solicitud, int dias){
        //Verificar como es el estado base de una solicitud
        if(!solicitud.getEstado().equals("PENDIENTE-MENTOR")){
            return new ResponseEntity<>(new RespuestaDTO(false,"Estado de solicitud incorrecto", null), HttpStatus.FORBIDDEN);
        }
        if(solicitud.getTipo().equals("UDEMY")){
            solicitud.setTiempoSolicitado(dias);
            solicitud.setEstado("PENDIENTE-ADMIN");
            solicitudRepository.save(solicitud);
            return new ResponseEntity<>(new RespuestaDTO(true,"Solicitud enviada al administrador", null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new RespuestaDTO(false,"Tipo de solicitud incorrecta", null), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> aceptarSolicitudSimple (Solicitud solicitud){
        //Verificar como es el estado base de una solicitud
        if(!solicitud.getEstado().equals("PENDIENTE-MENTOR")){
            return new ResponseEntity<>(new RespuestaDTO(false,"Estado de solicitud incorrecto", null), HttpStatus.FORBIDDEN);
        }
        if(solicitud.getTipo().equals("OTROS") || solicitud.getTipo().equals("ASESORAMIENTO")){
            solicitud.setEstado("PENDIENTE-ADMIN");

            solicitudRepository.save(solicitud);
            return new ResponseEntity<>(new RespuestaDTO(true,"Solicitud enviada al administrador", null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new RespuestaDTO(false,"Tipo de solicitud incorrecta", null), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> rechazarSolicitud (Solicitud solicitud){
        if(!solicitud.getEstado().equals("PENDIENTE-MENTOR")){
            return new ResponseEntity<>(new RespuestaDTO(false,"Estado de solicitud incorrecto", null), HttpStatus.FORBIDDEN);
        }

        solicitud.setEstado("RECHAZADO");
        solicitudRepository.save(solicitud);
        return new ResponseEntity<>(new RespuestaDTO(true,"Solicitud rechazada", null), HttpStatus.OK);

    }

    public ResponseEntity<?> devolverSolicitud (Solicitud solicitud){
        if(!solicitud.getEstado().equals("PENDIENTE-MENTOR")){
            return new ResponseEntity<>(new RespuestaDTO(false,"Estado de solicitud incorrecto", null), HttpStatus.FORBIDDEN);
        }

        solicitud.setEstado("DEVUELVE-USER");
        solicitudRepository.save(solicitud);
        return new ResponseEntity<>(new RespuestaDTO(true,"Solicitud devuelta al usuario", null), HttpStatus.OK);

    }

}
