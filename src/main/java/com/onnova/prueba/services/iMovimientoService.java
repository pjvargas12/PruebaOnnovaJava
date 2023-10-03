package com.onnova.prueba.services;

import com.onnova.prueba.model.Cuenta;
import com.onnova.prueba.model.Movimiento;
import com.onnova.prueba.response.MovimientoResponseRest;
import org.springframework.http.ResponseEntity;

public interface iMovimientoService {
    ResponseEntity<MovimientoResponseRest> save(Movimiento movimiento,
                                                Long cuenta_remitente,
                                                Long cuenta_receptora);
    ResponseEntity<MovimientoResponseRest> search();
}
