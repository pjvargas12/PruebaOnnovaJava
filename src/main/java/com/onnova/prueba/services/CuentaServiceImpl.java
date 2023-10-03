package com.onnova.prueba.services;

import com.onnova.prueba.dao.iClienteDao;
import com.onnova.prueba.dao.iCuentaDao;
import com.onnova.prueba.model.Cliente;
import com.onnova.prueba.model.Cuenta;
import com.onnova.prueba.response.CuentaResponseRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements iCuentaService{

    private final iCuentaDao cuentaDao;
    private final iClienteDao clienteDao;

    public CuentaServiceImpl(iCuentaDao cuentaDao, iClienteDao clienteDao) {
        this.cuentaDao = cuentaDao;
        this.clienteDao = clienteDao;
    }

    @Override
    @Transactional
    public ResponseEntity<CuentaResponseRest> save(Cuenta cuenta, Long cliente) {
        CuentaResponseRest response = new CuentaResponseRest();
        String tipoMoneda = cuenta.getTipo_moneda();

        if (!clienteDao.findById(cliente).isPresent()) {
            response.setMetadata("Respuesta nok", "-1", "No se encontró el usuario");
            return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (cuentaDao.searchByNumero(cuenta.getNumero()).size() > 0) {
            response.setMetadata("Respuesta nok", "-1", "ya existe una cuenta con tal número");
            return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (tipoMoneda.equals("PESO") && cuenta.getSaldo() > 1000000) {
            response.setMetadata("Respuesta nok", "-1", "El saldo de la cuenta no puede ser mayor a 1000000 Pesos");
            return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (tipoMoneda.equals("EURO") && cuenta.getSaldo() > 150) {
            response.setMetadata("Respuesta nok", "-1", "El saldo de la cuenta no puede ser mayor a 150 Euros");
            return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (tipoMoneda.equals("DOLAR") && cuenta.getSaldo() > 300) {
            response.setMetadata("Respuesta nok", "-1", "El saldo de la cuenta no puede ser mayor a 300 Dolares");
            return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else {
            try{
                Optional<Cliente> clienteCuenta = clienteDao.findById(cliente);
                // Guardar cuenta
                cuenta.setCliente(clienteCuenta.get());
                Cuenta cuentaSaved = cuentaDao.save(cuenta);
                if (cuentaSaved != null) {
                    List<Cuenta> list = new ArrayList<>();
                    list.add(cuentaSaved);
                    response.setCuenta(list);
                    response.setMetadata("Respuesta ok", "00", "Cuenta guardada");
                } else {
                    response.setMetadata("Respuesta nok", "-1", "No se ha podido guardar la cuenta");
                    return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e){
                e.getStackTrace();
                response.setMetadata("Respuesta nok", "-1", "Error al guardar cuenta");
                return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CuentaResponseRest> search() {
        CuentaResponseRest response = new CuentaResponseRest();
        List<Cuenta> list = new ArrayList<>();
        List<Cuenta> listAux = new ArrayList<>();
        try {
            //Buscar todas las cuentas
            listAux = (List<Cuenta>) cuentaDao.findAll();
            if (listAux.size()>0){
                listAux.stream().forEach((p)->{
                    list.add(p);
                });
                response.setCuenta(list);
                response.setMetadata("Respuesta ok", "00", "Cuentas cargadas");
            } else {
                response.setMetadata("Respuesta nok", "-1", "No se encontraron las cuentas");
                return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta nok", "-1", "Error al buscar cuentas");
            return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CuentaResponseRest> searchByNumero(Long id) {
        CuentaResponseRest response = new CuentaResponseRest();
        List<Cuenta> list = new ArrayList<>();
        List<Cuenta> listAux = new ArrayList<>();
        try {
            //Buscar la cuenta
            listAux = (List<Cuenta>) cuentaDao.searchByNumero(id);
            if (listAux.size()>0){
                listAux.stream().forEach((p)->{
                    list.add(p);
                });
                response.setCuenta(list);
                response.setMetadata("Respuesta ok", "00", "Cuenta encontrado");
            } else {
                response.setMetadata("Respuesta nok", "-1", "No se encontró la cuenta");
                return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta nok", "-1", "Error al buscar cuenta");
            return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CuentaResponseRest> update(Cuenta cuenta, Long cliente, Long id) {
        CuentaResponseRest response = new CuentaResponseRest();
        String tipoMoneda = cuenta.getTipo_moneda();
        if (!clienteDao.findById(cliente).isPresent()) {
            response.setMetadata("Respuesta nok", "-1", "No se encontró el usuario");
            return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (cuentaDao.searchByNumero(cuenta.getNumero()).size() > 0) {
            response.setMetadata("Respuesta nok", "-1", "ya existe una cuenta con tal número");
            return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (tipoMoneda.equals("PESO")|| cuenta.getSaldo() > 1000000) {
            response.setMetadata("Respuesta nok", "-1", "El saldo de la cuenta no puede ser mayor a 1000000 Pesos");
            return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (tipoMoneda.equals("EURO")|| cuenta.getSaldo() > 150) {
            response.setMetadata("Respuesta nok", "-1", "El saldo de la cuenta no puede ser mayor a 150 Euros");
            return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (tipoMoneda.equals("DOLAR")|| cuenta.getSaldo() > 300) {
            response.setMetadata("Respuesta nok", "-1", "El saldo de la cuenta no puede ser mayor a 300 Dolares");
            return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else {
            try {
                //Buscar cuenta a actualizar
                Optional<Cuenta> cuentaSearch = cuentaDao.findById(id);
                if (cuentaSearch.isPresent()) {
                    //Se actualiza cuenta
                    cuentaSearch.get().setNumero(cuenta.getNumero());
                    cuentaSearch.get().setSaldo(cuenta.getSaldo());
                    cuentaSearch.get().setTipo_moneda(cuenta.getTipo_moneda());
                    cuentaSearch.get().setCliente(cuenta.getCliente());
                    Cuenta cuentaSaved = cuentaDao.save(cuentaSearch.get());
                    if (cuentaSaved != null) {
                        List<Cuenta> list = new ArrayList<>();
                        list.add(cuentaSaved);
                        response.setCuenta(list);
                        response.setMetadata("Respuesta ok", "00", "cuenta actualizada correctamente");
                    } else {
                        response.setMetadata("Respuesta nok", "-1", "cuenta no actualizada");
                        return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    response.setMetadata("Respuesta nok", "-1", "No se encontró la cuenta a actualizar");
                    return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.NOT_FOUND);
                }
            } catch (Exception e){
                e.getStackTrace();
                response.setMetadata("Respuesta nok", "-1", "Error al actualizar cuenta");
                return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CuentaResponseRest> delete(Long id) {
        CuentaResponseRest response = new CuentaResponseRest();
        try {
            cuentaDao.deleteById(id);
            response.setMetadata("Respuesta OK", "00", "Se eliminó la cuenta");
        } catch (Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta nok", "-1", "Error al eliminar cuenta");
            return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CuentaResponseRest>(response, HttpStatus.OK);
    }
}
