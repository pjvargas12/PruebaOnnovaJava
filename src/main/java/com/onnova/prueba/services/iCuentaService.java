package com.onnova.prueba.services;

import com.onnova.prueba.model.Cuenta;
import com.onnova.prueba.response.CuentaResponseRest;
import org.springframework.http.ResponseEntity;

public interface iCuentaService {
    ResponseEntity<CuentaResponseRest> save(Cuenta cuenta,
                                            Long cliente);
    ResponseEntity<CuentaResponseRest> search();
    ResponseEntity<CuentaResponseRest> searchByNumero(Long id);
    ResponseEntity<CuentaResponseRest> update(Cuenta cuenta,
                                              Long cliente,
                                              Long id);
    ResponseEntity<CuentaResponseRest> delete(Long id);
}
