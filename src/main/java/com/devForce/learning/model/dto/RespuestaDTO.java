package com.devForce.learning.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RespuestaDTO {

    private boolean ok;
    private String mensaje;
    private Object content;


    // Si el rol es Usuario devolver el tipo, descripcion y estado de sus solicitudes

    // Si el rol es Mentor o Admin devolver el Usuario, tipo de solicitud y descripcion de cada solicitud


    

}