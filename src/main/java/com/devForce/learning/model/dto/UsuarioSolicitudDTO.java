package com.devForce.learning.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioSolicitudDTO {
    private String nombre, apellido, correo, tel;
    private boolean tieneTeams;

}
