package com.devForce.learning.model.dto;

import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioDTO {

    private long id;

    private String nombre, apellido, username, mail, phone, rol;

    private List<Solicitud> solicitudes;

    private boolean hasTeams;


}
