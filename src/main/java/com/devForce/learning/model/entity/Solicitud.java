package com.devForce.learning.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "solicitud")
public class Solicitud {

    @Id
    @GeneratedValue
    private long solicitudId;

    @Column(name = "tipo", length = 25, nullable = false)
    private String tipo;

    @Column(name = "descripcion", length = 500, nullable = false)
    private String descripcion;

    @Column(name = "apruebaMentorID")
    private int apruebaMentorID;

    @Column(name = "apruebaAdminID")
    private int apruebaAdminID;

    @Column(name = "estado", length = 25, nullable = false)
    private String estado;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    @JsonIgnore
    public Usuario getUsuario() {
        return usuario;
    }

}
