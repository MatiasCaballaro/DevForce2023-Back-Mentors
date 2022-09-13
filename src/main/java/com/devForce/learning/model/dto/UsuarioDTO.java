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

    private String nombre, apellido, username, mail, phone, rol;

    //TODO: ES solicitudDTO?
    private List<Solicitud> solicitudes;

    private boolean hasTeams;

}
