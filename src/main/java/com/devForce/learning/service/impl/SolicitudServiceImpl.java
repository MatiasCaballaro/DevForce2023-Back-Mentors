package com.devForce.learning.service.impl;

import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.dto.SolicitudMentorAdminDTO;
import com.devForce.learning.model.dto.SolicitudUsuarioDTO;
import com.devForce.learning.model.dto.UsuarioSolicitudDTO;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public ResponseEntity<String> crearSolicitud(Usuario usuario, Solicitud solicitud) {
        if (usuario.getRol()=="Usuario"||usuario.getRol()=="Mentor"){
            return new ResponseEntity<String>("Solicitud creada", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<String>("No tienes permisos crack", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<?> devolverSolicitudesUsuario(Usuario usuario) {
        //TODO: Verificar que el usuario es el logueado
        List<Solicitud> solicitudes = usuario.getSolicitudes();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        respuestaDTO.setOk(true);
        respuestaDTO.setMensaje("");
        List<SolicitudUsuarioDTO> solicitudUsuarioDTOS = new ArrayList<>();
        SolicitudUsuarioDTO  solicitudUsuarioDTO = new SolicitudUsuarioDTO();
        for (Solicitud solicitud : solicitudes) {
            solicitudUsuarioDTO.setTipo(solicitud.getTipo());
            solicitudUsuarioDTO.setDescripcion(solicitud.getDescripcion());
            solicitudUsuarioDTO.setEstado(solicitud.getEstado());

            solicitudUsuarioDTOS.add(solicitudUsuarioDTO);
        }
        respuestaDTO.setContenido(solicitudUsuarioDTOS);
        return new ResponseEntity<>(respuestaDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> devolverSolicitudesMentorAdmin(Usuario usuario) {
        //TODO: Verificar que el usuario está logueado
        List<Solicitud> listaSolicitudes = solicitudRepository.findAll();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        respuestaDTO.setOk(true);
        respuestaDTO.setMensaje("");
        List<SolicitudMentorAdminDTO> solicitudMentorAdminDTOS = new ArrayList<>();
        // Es mejor hacer sólo un new por tema de memoria? O es mejor crear una nueva instancia de cada DTO y pasarle ahí los parámentros
        // con el constructor con parámetros?
        SolicitudMentorAdminDTO solicitudMentorAdminDTO = new SolicitudMentorAdminDTO();
        UsuarioSolicitudDTO usuarioSolicitudDTO = new UsuarioSolicitudDTO();
        for (Solicitud solicitud : listaSolicitudes) {
            if(solicitud.getUsuario().getId() != usuario.getId()){
                usuarioSolicitudDTO.setApellido(usuario.getApellido());
                usuarioSolicitudDTO.setNombre(usuario.getNombre());
                usuarioSolicitudDTO.setCorreo(usuario.getMail());
                usuarioSolicitudDTO.setTel(usuario.getPhone());
                usuarioSolicitudDTO.setTieneTeams(usuario.getHasTeams());

                solicitudMentorAdminDTO.setUsuario(usuarioSolicitudDTO);
                solicitudMentorAdminDTO.setTipo(solicitud.getTipo());
                solicitudMentorAdminDTO.setDescripcion(solicitud.getDescripcion());

                solicitudMentorAdminDTOS.add(solicitudMentorAdminDTO);
            }
        }
        respuestaDTO.setContenido(solicitudMentorAdminDTOS);
        return new ResponseEntity<>(respuestaDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getTiposDeSolicitud() {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        respuestaDTO.setOk(true);
        respuestaDTO.setMensaje("");
        // TODO: Preguntar dónde estarían estos datos
        return null;
    }

    @Override
    public ResponseEntity<?> getAreasDeSolicitud() {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        respuestaDTO.setOk(true);
        respuestaDTO.setMensaje("");
        // TODO: Preguntar dónde estarían estos datos
        return null;
    }

    @Override
    public ResponseEntity<?> getSolicitudesByIdUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

        usuario.setRol(usuario.getRol().toUpperCase());

        if(usuario != null) {
            if (usuario.getRol().equals("USUARIO"))
                return devolverSolicitudesUsuario(usuario);
            else
                return devolverSolicitudesMentorAdmin(usuario);
        } else {
            return error("El usuario es nulo");
        }
    }

    @Override
    public ResponseEntity<?> error(String mensaje) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        respuestaDTO.setOk(false);
        respuestaDTO.setMensaje(mensaje);
        respuestaDTO.setContenido(null);
        return new ResponseEntity<>(respuestaDTO, HttpStatus.BAD_REQUEST);
    }



}
