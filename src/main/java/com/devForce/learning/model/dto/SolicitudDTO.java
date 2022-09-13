package com.devForce.learning.model.dto;

import com.devForce.learning.model.entity.Licencia;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SolicitudDTO {

    //TODO: CHEQUEAR RECURSIVIDAD, capaz no es necesario mostrar el usuario o la licencia

    private long id;

    private String tipo, descripcion, estado, area;

    private int apruebaMentorID, apruebaAdminID, tiempoSolicitado;

    private UsuarioDTO usuario;

    private LicenciaDTO licencia;

}
