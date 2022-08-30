package com.devForce.learning.repository;

import com.devForce.learning.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Usuario findByNombreAndApellido (String nombre, String Apellido);

    Usuario findByRolAndUsername (String rol, String username);
}
