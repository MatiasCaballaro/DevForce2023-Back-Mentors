package com.devForce.learning.service.impl;

import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.dto.SolicitudDTO;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.SolicitudRepository;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.security.services.UserDetailsImpl;
import com.devForce.learning.service.SolicitudService;
import com.devForce.learning.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    /*
    @Override
    public ResponseEntity<?> crearSolicitudOld(Solicitud solicitud) {
        Usuario usuario = solicitud.getUsuario();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
//        if (!usuario.getRol().equals("MENTOR") && !usuario.getRol().equals("USER")){
//            respuestaDTO.setOk(false);
//            respuestaDTO.setMensaje("No OK");
//            return new ResponseEntity<>(respuestaDTO, HttpStatus.FORBIDDEN);
//        }
        solicitud.setEstado("PENDIENTE-MENTOR");
        solicitudRepository.save(solicitud);
        respuestaDTO.setOk(true);
        respuestaDTO.setMensaje("OK");
        return new ResponseEntity<>(respuestaDTO, HttpStatus.OK);
    }

     */

    @Override
    public ResponseEntity<?> crearSolicitud(@Valid Solicitud solicitud, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Usuario usuario = usuarioRepository.findById(userDetails.getId()).get();
        Solicitud newSolicitud = new Solicitud(
                solicitud.getTipo(),
                solicitud.getDescripcion(),
                solicitud.getArea(),
                usuario);

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        newSolicitud.setEstado("PENDIENTE-MENTOR");
        solicitudRepository.save(newSolicitud);
        respuestaDTO.setOk(true);
        respuestaDTO.setMensaje("Solicitud Creada");
        respuestaDTO.setContenido(solicitud);
        return new ResponseEntity<>(respuestaDTO, HttpStatus.OK);
    }

    /*
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
                usuarioSolicitudDTO.setCorreo(usuario.getEmail());
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
    */


    // TODO: Terminar getTiposDeSolicitud() y getAreasDeSolicitud
    @Override
    public ResponseEntity<?> getTiposDeSolicitud() {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        respuestaDTO.setOk(true);
        respuestaDTO.setMensaje("");

        return null;
    }

    @Override
    public ResponseEntity<?> getAreasDeSolicitud() {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        respuestaDTO.setOk(true);
        respuestaDTO.setMensaje("");

        return null;
    }

    /*
    @Override
    public ResponseEntity<?> getSolicitudesByIdUsuario(Long idUsuario) {
      /*  Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

        usuario.setRol(usuario.getRol().toUpperCase());

        if(usuario != null) {
            if (usuario.getRol().equals("USUARIO"))
                return devolverSolicitudesUsuario(usuario);
            else
                return devolverSolicitudesMentorAdmin(usuario);
        } else {
            return error("El usuario es nulo");
        }
        return error("El usuario es nulo");
    }
    */

    @Override
    public SolicitudDTO crearSolicitudDTO(Solicitud solicitud) {

        SolicitudDTO dto = new SolicitudDTO();
        dto.setId(solicitud.getId());
        dto.setDescripcion(solicitud.getDescripcion());
        dto.setEstado(solicitud.getEstado());
        dto.setTipo(solicitud.getTipo());
        dto.setApruebaAdminID(solicitud.getApruebaAdminID());
        dto.setApruebaMentorID(solicitud.getApruebaMentorID());
        dto.setTiempoSolicitado(solicitud.getTiempoSolicitado());
        dto.setArea(solicitud.getArea());
        dto.setUsuario(usuarioService.crearUsuarioDTO(solicitud.getUsuario()));

        return dto;
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
