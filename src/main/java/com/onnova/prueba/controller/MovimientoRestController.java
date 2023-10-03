package com.onnova.prueba.controller;

import com.onnova.prueba.model.Cuenta;
import com.onnova.prueba.model.Movimiento;
import com.onnova.prueba.response.CuentaResponseRest;
import com.onnova.prueba.response.MovimientoResponseRest;
import com.onnova.prueba.services.iMovimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = {"*"})
@RequestMapping("/movimiento")
@RestController
public class MovimientoRestController {

    private final iMovimientoService service;

    public MovimientoRestController(iMovimientoService service) {
        this.service = service;
    }


    @PostMapping("/save")
    public ResponseEntity<MovimientoResponseRest> save(@RequestParam("id_cuenta_remitente") Long id_cuenta_remitente,
                                                       @RequestParam("id_cuenta_receptora") Long id_cuenta_receptora,
                                                       @RequestParam("valor") Long valor) {
        Movimiento movimiento = new Movimiento();
        movimiento.setValor(valor);
        movimiento.setFecha(new Date());
        ResponseEntity<MovimientoResponseRest> response = service.save(movimiento, id_cuenta_remitente, id_cuenta_receptora);
        return response;
    }

    @GetMapping("/search")
    public ResponseEntity<MovimientoResponseRest> search() {
        ResponseEntity<MovimientoResponseRest> response = service.search();
        return response;
    }


}
