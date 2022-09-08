package com.devForce.learning.repository;


import com.devForce.learning.model.entity.Solicitud;
import com.devForce.learning.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud,Long> {

    Solicitud findById (long Id);

    //List<Solicitud> findByIdUsuario (long IdUsuario);
    List<Solicitud> findByUsuarioAndEstado (Usuario usuario,String estado);

}
