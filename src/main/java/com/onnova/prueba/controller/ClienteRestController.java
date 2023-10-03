package com.onnova.prueba.controller;

import com.onnova.prueba.model.Cliente;
import com.onnova.prueba.response.ClienteResponseRest;
import com.onnova.prueba.services.iClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = {"*"})
@RequestMapping("/cliente")
@RestController
public class ClienteRestController {

    private final iClienteService service;

    public ClienteRestController(iClienteService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<ClienteResponseRest> save(@RequestParam("documento") Long documento,
                                                    @RequestParam("rut") Long rut,
                                                    @RequestParam(required = false, name = "nombre") String nombre,
                                                    @RequestParam(required = false, name = "apellido") String apellido,
                                                    @RequestParam(required = false, name = "razon_social") String razon_social,
                                                    @RequestParam(required = false, name = "ano_fundacion") Integer ano_fundacion,
                                                    @RequestParam("tipo_documento") String tipo_documento,
                                                    @RequestParam("tipo_cliente") String tipo_cliente) {
        Cliente cliente = new Cliente();
        cliente.setDocumento(documento);
        cliente.setRut(rut);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setRazon_social(razon_social);
        cliente.setAno_fundacion(ano_fundacion);
        cliente.setTipo_documento(tipo_documento);
        cliente.setTipo_cliente(tipo_cliente);
        ResponseEntity<ClienteResponseRest> response = service.save(cliente);
        return response;
    }

    @GetMapping("/search")
    public ResponseEntity<ClienteResponseRest> search() {
        ResponseEntity<ClienteResponseRest> response = service.search();
        return response;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClienteResponseRest> update(@RequestParam("documento") Long documento,
                                                      @RequestParam("rut") Long rut,
                                                      @RequestParam(required = false, name = "nombre") String nombre,
                                                      @RequestParam(required = false, name = "apellido") String apellido,
                                                      @RequestParam(required = false, name = "razon_social") String razon_social,
                                                      @RequestParam(required = false, name = "ano_fundacion") Integer ano_fundacion,
                                                      @RequestParam("tipo_documento") String tipo_documento,
                                                      @RequestParam("tipo_cliente") String tipo_cliente,
                                                      @PathVariable Long id) {
        Cliente cliente = new Cliente();
        cliente.setDocumento(documento);
        cliente.setRut(rut);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setRazon_social(razon_social);
        cliente.setAno_fundacion(ano_fundacion);
        cliente.setTipo_documento(tipo_documento);
        cliente.setTipo_cliente(tipo_cliente);
        ResponseEntity<ClienteResponseRest> response = service.update(cliente, id);
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ClienteResponseRest> delete(@PathVariable Long id) {
        ResponseEntity<ClienteResponseRest> response = service.delete(id);
        return response;
    }

    @GetMapping("search/{rut}")
    public ResponseEntity<ClienteResponseRest> searchByPerId(@PathVariable Long rut) {
        ResponseEntity<ClienteResponseRest> response = service.searchByRut(rut);
        return response;
    }
}
