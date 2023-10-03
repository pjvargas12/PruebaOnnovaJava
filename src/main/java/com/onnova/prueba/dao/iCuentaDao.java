package com.onnova.prueba.dao;

import com.onnova.prueba.model.Cliente;
import com.onnova.prueba.model.Cuenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface iCuentaDao extends CrudRepository<Cuenta, Long> {

    @Query(value = "SELECT cue.* FROM t_cuenta cue WHERE cue.numero = ?1", nativeQuery = true)
    List<Cuenta> searchByNumero(Long id);
}
