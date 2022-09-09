package com.devForce.learning.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioSolicitudDTO {

    private String nombre;

    private String apellido;

    private String correo;

    private String tel;

    private boolean tieneTeams;

}
