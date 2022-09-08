package com.devForce.learning.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString (exclude = { "solicitudes"})
@Table(name = "licencia")
public class Licencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "serial", length = 255)
    private String serial;

    @Column(name = "occupation", length = 25)
    private String occupation;

    @Column(name = "expdate")
    private LocalDateTime expdate;

    @Column(name = "platform", length = 25)
    private String platform;

    //Relación con solicitud
    @OneToMany(mappedBy="licencia", fetch=FetchType.EAGER)
    private List<Solicitud> solicitudes;


}
