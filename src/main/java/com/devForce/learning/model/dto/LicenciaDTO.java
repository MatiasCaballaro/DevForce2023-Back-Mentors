package com.devForce.learning.model.dto;

import com.devForce.learning.model.entity.Solicitud;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LicenciaDTO {

    private long id;

    private String serie, estado, plataforma;

    private LocalDateTime vencimiento;

    private List<SolicitudDTO> solicitudes;

}
