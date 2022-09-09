package com.devForce.learning.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SolicitudUsuarioDTO {

    @JsonProperty("Tipo de solicitud")
    private String tipo;

    @JsonProperty("Descripcion")
    private String descripcion;

    @JsonProperty("Estado")
    private String estado;
}
