package com.devForce.learning.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", length = 50, nullable = false)
    private String nombre;

    @Column(name = "lastname", length = 50, nullable = false)
    private String Apellido;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "mail", length = 100)
    private String mail;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "role", length = 25, nullable = false)
    private String rol;

    @Column(name = "hasTeams")
    private Boolean hasTeams;


    @OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
    private Set<Solicitud> solicitudes;


}
