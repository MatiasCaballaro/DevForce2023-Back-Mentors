package com.devForce.learning.repository;

import com.devForce.learning.model.entity.Licencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicenciaRepository extends JpaRepository <Licencia, Long> {

    Licencia findById(long id);
    Licencia findBySerial(String serial);

    Licencia findFirstByOccupationOrderById(String occupation);
    List<Licencia> findByOccupation(String occupation);



}
