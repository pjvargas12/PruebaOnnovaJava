package com.onnova.prueba.dao;

import com.onnova.prueba.model.Movimiento;
import org.springframework.data.repository.CrudRepository;

public interface iMovimientoDao extends CrudRepository<Movimiento, Long> {
}
