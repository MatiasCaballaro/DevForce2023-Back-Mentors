package com.devForce.learning.service.impl;

import com.devForce.learning.model.dto.RespuestaDTO;
import com.devForce.learning.model.dto.SolicitudDTO;
import com.devForce.learning.model.dto.UsuarioDTO;
import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import com.devForce.learning.repository.UsuarioRepository;
import com.devForce.learning.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;



    //TODO revisar este método (lógica) tal vez pueda funcionar con el id del authentication
    @Override
    public ResponseEntity<RespuestaDTO> updateDatos(Usuario usuario) {
        //TODO: Cuando se realice la authentication, se tiene que validar que el usuario a actualizar coincide con el logueado

        Usuario updateUser = usuarioRepository.findByNombreAndApellido(usuario.getNombre(), usuario.getApellido());

        if(updateUser == null){
            return error("Usuario no existe");
        }

        updateUser.setPhone(usuario.getPhone());
        updateUser.setEmail(usuario.getEmail());
        updateUser.setHasTeams(usuario.getHasTeams());
        updateUser.setPassword(usuario.getPassword());

        usuarioRepository.save(updateUser);

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        respuestaDTO.setOk(true);
        respuestaDTO.setMensaje("Datos actualizados correctamente");
        respuestaDTO.setContenido(updateUser);

        return new ResponseEntity<>(respuestaDTO,HttpStatus.OK);

    }

    @Override
    public UsuarioDTO crearUsuarioDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setUsername(usuario.getUsername());
        dto.setMail(usuario.getEmail());
        dto.setPhone(usuario.getPhone());
        dto.setHasTeams(usuario.getHasTeams());
        /*
        dto.setSolicitudes(
                usuario.getSolicitudes()
                        .stream()
                        .map(s -> solicitudService.crearSolicitudDTO(s))
                        .collect(Collectors.toList()));
        */

        return dto;
    }

    @Override
    public List<UsuarioDTO> allUsersDTO() {
        return usuarioRepository.findAll().stream().map(u->crearUsuarioDTO(u)).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<RespuestaDTO> error(String mensaje) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        respuestaDTO.setOk(false);
        respuestaDTO.setMensaje(mensaje);
        respuestaDTO.setContenido(null);
        return new ResponseEntity<>(respuestaDTO, HttpStatus.BAD_REQUEST);
    }
}
