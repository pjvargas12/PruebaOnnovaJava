package com.onnova.prueba.controller;

import com.onnova.prueba.model.Cuenta;
import com.onnova.prueba.response.CuentaResponseRest;
import com.onnova.prueba.services.iCuentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RequestMapping("/cuenta")
@RestController
public class CuentaRestController {
    
    private final iCuentaService service;

    public CuentaRestController(iCuentaService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<CuentaResponseRest> save(@RequestParam("numero") Long numero,
                                                    @RequestParam("saldo") Long saldo,
                                                    @RequestParam("tipo_moneda") String tipo_moneda,
                                                    @RequestParam("id_cliente") Long id_cliente) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero(numero);
        cuenta.setSaldo(saldo);
        cuenta.setTipo_moneda(tipo_moneda);
        ResponseEntity<CuentaResponseRest> response = service.save(cuenta, id_cliente);
        return response;
    }

    @GetMapping("/search")
    public ResponseEntity<CuentaResponseRest> search() {
        ResponseEntity<CuentaResponseRest> response = service.search();
        return response;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CuentaResponseRest> update(@RequestParam("numero") Long numero,
                                                     @RequestParam("saldo") Long saldo,
                                                     @RequestParam("tipo_moneda") String tipo_moneda,
                                                     @RequestParam("tipo_cliente") Long tipo_cliente,
                                                      @PathVariable Long id) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero(numero);
        cuenta.setSaldo(saldo);
        cuenta.setTipo_moneda(tipo_moneda);
        ResponseEntity<CuentaResponseRest> response = service.update(cuenta, tipo_cliente, id);
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CuentaResponseRest> delete(@PathVariable Long id) {
        ResponseEntity<CuentaResponseRest> response = service.delete(id);
        return response;
    }

    @GetMapping("search/{numero}")
    public ResponseEntity<CuentaResponseRest> searchByPerId(@PathVariable Long numero) {
        ResponseEntity<CuentaResponseRest> response = service.searchByNumero(numero);
        return response;
    }
    
}
