package com.devForce.learning.model.dto;

import com.devForce.learning.model.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SolicitudMentorAdminDTO {

    private UsuarioSolicitudDTO usuario;

    @JsonProperty("Tipo de solicitud")
    private String tipo;

    @JsonProperty("Descripcion")
    private String descripcion;

}
