package com.onnova.prueba.services;

import com.onnova.prueba.model.Cliente;
import com.onnova.prueba.response.ClienteResponseRest;
import org.springframework.http.ResponseEntity;

public interface iClienteService {

    ResponseEntity<ClienteResponseRest> save(Cliente cliente);
    ResponseEntity<ClienteResponseRest> search();
    ResponseEntity<ClienteResponseRest> searchByRut(Long id);
    ResponseEntity<ClienteResponseRest> update(Cliente cliente,Long id);
    ResponseEntity<ClienteResponseRest> delete(Long id);
}
