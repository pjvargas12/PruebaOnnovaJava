package com.onnova.prueba.services;

import com.onnova.prueba.dao.iCuentaDao;
import com.onnova.prueba.dao.iMovimientoDao;
import com.onnova.prueba.model.Cuenta;
import com.onnova.prueba.model.Movimiento;
import com.onnova.prueba.response.CuentaResponseRest;
import com.onnova.prueba.response.MovimientoResponseRest;
import com.onnova.prueba.response.MovimientoResponseRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements iMovimientoService{

    private final iMovimientoDao movimientoDao;
    private final iCuentaDao cuentaDao;

    public MovimientoServiceImpl(iMovimientoDao movimientoDao, iCuentaDao cuentaDao) {
        this.movimientoDao = movimientoDao;
        this.cuentaDao = cuentaDao;
    }

    @Override
    @Transactional
    public ResponseEntity<MovimientoResponseRest> save(Movimiento movimiento, Long cuenta_remitente, Long cuenta_receptora) {
        MovimientoResponseRest response = new MovimientoResponseRest();
        CuentaResponseRest responseCuenta = new CuentaResponseRest();
        Optional<Cuenta> cuentaRemitente = cuentaDao.findById(cuenta_remitente);
        Optional<Cuenta> cuentaReceptora = cuentaDao.findById(cuenta_receptora);
        if (!cuentaRemitente.isPresent()) {
            response.setMetadata("Respuesta nok", "-1", "No existe la cuenta remitente");
            return new ResponseEntity<MovimientoResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (!cuentaReceptora.isPresent()) {
            response.setMetadata("Respuesta nok", "-1", "No existe la cuenta receptora");
            return new ResponseEntity<MovimientoResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (cuentaRemitente.get().getSaldo() < movimiento.getValor()) {
            response.setMetadata("Respuesta nok", "-1", "La cuenta remitente no cuenta con suficienta saldo");
            return new ResponseEntity<MovimientoResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (cuentaReceptora.get().getTipo_moneda().equals("PESO") && (cuentaReceptora.get().getSaldo() + movimiento.getValor()) > 1000000) {
            response.setMetadata("Respuesta nok", "-1", "EL saldo de la cuenta receptora excede el monto fijo para PESOS ($1000000)");
            return new ResponseEntity<MovimientoResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (cuentaReceptora.get().getTipo_moneda().equals("EURO") && (cuentaReceptora.get().getSaldo() + movimiento.getValor()) > 150) {
            response.setMetadata("Respuesta nok", "-1", "EL saldo de la cuenta receptora excede el monto fijo para EURO ($150)");
            return new ResponseEntity<MovimientoResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (cuentaReceptora.get().getTipo_moneda().equals("DOLAR") && (cuentaReceptora.get().getSaldo() + movimiento.getValor()) > 300) {
            response.setMetadata("Respuesta nok", "-1", "EL saldo de la cuenta receptora excede el monto fijo para EURO ($150)");
            return new ResponseEntity<MovimientoResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else {
            try{
                // Guardar Movimientos
                movimiento.setCuenta_remitente(cuentaRemitente.get());
                movimiento.setCuenta_receptora(cuentaReceptora.get());
                Movimiento movimientoSaved = movimientoDao.save(movimiento);
                if (movimientoSaved != null) {;
                    List<Movimiento> list = new ArrayList<>();
                    list.add(movimientoSaved);
                    response.setMovimiento(list);
                    cuentaRemitente.get().setNumero(cuentaRemitente.get().getNumero());
                    cuentaRemitente.get().setSaldo(cuentaRemitente.get().getSaldo()-movimiento.getValor());
                    cuentaRemitente.get().setTipo_moneda(cuentaRemitente.get().getTipo_moneda());
                    cuentaRemitente.get().setCliente(cuentaRemitente.get().getCliente());
                    Cuenta cuentaSavedRemitente = cuentaDao.save(cuentaRemitente.get());
                    cuentaReceptora.get().setNumero(cuentaReceptora.get().getNumero());
                    cuentaReceptora.get().setSaldo(cuentaReceptora.get().getSaldo()+movimiento.getValor());
                    cuentaReceptora.get().setTipo_moneda(cuentaReceptora.get().getTipo_moneda());
                    cuentaReceptora.get().setCliente(cuentaReceptora.get().getCliente());
                    Cuenta cuentaSavedReceptora = cuentaDao.save(cuentaReceptora.get());
                    if (cuentaSavedReceptora != null && cuentaSavedRemitente != null) {
                        List<Cuenta> listCuenta = new ArrayList<>();
                        listCuenta.add(cuentaSavedReceptora);
                        listCuenta.add(cuentaSavedRemitente);
                        responseCuenta.setCuenta(listCuenta);
                        response.setMetadata("Respuesta ok", "00", "cuenta actualizada correctamente");
                    } else {
                        response.setMetadata("Respuesta nok", "-1", "cuenta no actualizada");
                        return new ResponseEntity<MovimientoResponseRest>(response, HttpStatus.BAD_REQUEST);
                    }
                    response.setMetadata("Respuesta ok", "00", "Movimiento guardado");
                } else {
                    response.setMetadata("Respuesta nok", "-1", "No se ha podido guardar el movimiento");
                    return new ResponseEntity<MovimientoResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e){
                e.getStackTrace();
                response.setMetadata("Respuesta nok", "-1", "Error al guardar movimiento");
                return new ResponseEntity<MovimientoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<MovimientoResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<MovimientoResponseRest> search() {
        MovimientoResponseRest response = new MovimientoResponseRest();
        List<Movimiento> list = new ArrayList<>();
        List<Movimiento> listAux = new ArrayList<>();
        try {
            //Buscar todas las cuentas
            listAux = (List<Movimiento>) movimientoDao.findAll();
            if (listAux.size()>0){
                listAux.stream().forEach((p)->{
                    list.add(p);
                });
                response.setMovimiento(list);
                response.setMetadata("Respuesta ok", "00", "Movimientos cargadas");
            } else {
                response.setMetadata("Respuesta nok", "-1", "No se encontraron los movimientos");
                return new ResponseEntity<MovimientoResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta nok", "-1", "Error al buscar movimientos");
            return new ResponseEntity<MovimientoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<MovimientoResponseRest>(response, HttpStatus.OK);
    }
}
