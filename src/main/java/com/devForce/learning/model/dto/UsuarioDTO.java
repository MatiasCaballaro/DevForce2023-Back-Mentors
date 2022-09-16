package com.devForce.learning.model.dto;

import com.devForce.learning.model.entity.Solicitud;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioDTO {

    private long id;

    //TODO modificar rol a Set
    private String nombre, apellido, username, mail, phone, rol;

    //private List<SolicitudDTO> solicitudes;

    private boolean hasTeams;

}
