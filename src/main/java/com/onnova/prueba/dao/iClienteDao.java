package com.onnova.prueba.dao;

import com.onnova.prueba.model.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface iClienteDao extends CrudRepository<Cliente, Long> {

    @Query(value = "SELECT cli.* FROM t_cliente cli WHERE cli.rut = ?1", nativeQuery = true)
    List<Cliente> searchByRut(Long id);

}
