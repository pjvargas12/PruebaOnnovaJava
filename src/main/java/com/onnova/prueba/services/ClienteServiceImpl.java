package com.onnova.prueba.services;

import com.onnova.prueba.dao.iClienteDao;
import com.onnova.prueba.model.Cliente;
import com.onnova.prueba.response.ClienteResponseRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements iClienteService{

    private final iClienteDao clienteDao;

    public ClienteServiceImpl(iClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @Override
    @Transactional
    public ResponseEntity<ClienteResponseRest> save(Cliente cliente) {
        ClienteResponseRest response = new ClienteResponseRest();
        String tipoDoc = cliente.getTipo_documento();
        String tipoClinte = cliente.getTipo_cliente();
        if (clienteDao.searchByRut(cliente.getRut()).size() > 0) {
            response.setMetadata("Respuesta nok", "-1", "ya hay un cliente con ese RUT");
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
        }else if (!(tipoDoc.equals("NIT")|| tipoDoc.equals("CC")||tipoDoc.equals("CE"))) {
            response.setMetadata("Respuesta nok", "-1", "Tipo de documento incorrecta (CC, NIT, CE)");
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (!(tipoClinte.equals("NATURAL") || tipoClinte.equals("JURIDICA"))) {
            response.setMetadata("Respuesta nok", "-1", "Tipo de cliente incorrecto");
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (tipoClinte.equals("NATURAL") && (cliente.getNombre().isEmpty() || cliente.getApellido().isEmpty())) {
            response.setMetadata("Respuesta nok", "-1", "Tipo de cliente NATURAL debe tener nombre y apellido");
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (tipoClinte.equals("JURIDICA") && (cliente.getRazon_social().isEmpty() || cliente.getAno_fundacion() == null)) {
            response.setMetadata("Respuesta nok", "-1", "Tipo de cliente JURIDICA debe tener razón social y año fundación");
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else {
            try{
                // Guardar cliente
                Cliente clienteSaved = clienteDao.save(cliente);
                if (clienteSaved != null) {
                    List<Cliente> list = new ArrayList<>();
                    list.add(clienteSaved);
                    response.setCliente(list);
                    response.setMetadata("Respuesta ok", "00", "Cliente guardado");
                } else {
                    response.setMetadata("Respuesta nok", "-1", "No se ha podido guardar el cliente");
                    return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e){
                e.getStackTrace();
                response.setMetadata("Respuesta nok", "-1", "Error al guardar cliente");
                return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ClienteResponseRest> search() {
        ClienteResponseRest response = new ClienteResponseRest();
        List<Cliente> list = new ArrayList<>();
        List<Cliente> listAux = new ArrayList<>();
        try {
            //Buscar todas las cliente
            listAux = (List<Cliente>) clienteDao.findAll();
            if (listAux.size()>0){
                listAux.stream().forEach((p)->{
                    list.add(p);
                });
                response.setCliente(list);
                response.setMetadata("Respuesta ok", "00", "Clientes cargadas");
            } else {
                response.setMetadata("Respuesta nok", "-1", "No se encontraron clientes");
                return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta nok", "-1", "Error al buscar clientes");
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ClienteResponseRest> searchByRut(Long id) {
        ClienteResponseRest response = new ClienteResponseRest();
        List<Cliente> list = new ArrayList<>();
        List<Cliente> listAux = new ArrayList<>();
        try {
            //Buscar todas las cliente
            listAux = (List<Cliente>) clienteDao.searchByRut(id);
            if (listAux.size()>0){
                listAux.stream().forEach((p)->{
                    list.add(p);
                });
                response.setCliente(list);
                response.setMetadata("Respuesta ok", "00", "Cliente encontrado");
            } else {
                response.setMetadata("Respuesta nok", "-1", "No se encontró el cliente");
                return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta nok", "-1", "Error al buscar cliente");
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ClienteResponseRest> update(Cliente cliente, Long id) {
        ClienteResponseRest response = new ClienteResponseRest();
        String tipoDoc = cliente.getTipo_documento();
        String tipoClinte = cliente.getTipo_cliente();
        if (clienteDao.searchByRut(cliente.getRut()).size() > 0) {
            response.setMetadata("Respuesta nok", "-1", "ya hay un cliente con ese RUT");
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
        }else if (!(tipoDoc.equals("NIT")|| tipoDoc.equals("CC")||tipoDoc.equals("CE"))) {
            response.setMetadata("Respuesta nok", "-1", "Tipo de documento incorrecta (CC, NIT, CE)");
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (!(tipoClinte.equals("NATURAL") || tipoClinte.equals("JURIDICA"))) {
            response.setMetadata("Respuesta nok", "-1", "Tipo de cliente incorrecto");
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (tipoClinte.equals("NATURAL") && (cliente.getNombre().isEmpty() || cliente.getApellido().isEmpty())) {
            response.setMetadata("Respuesta nok", "-1", "Tipo de cliente NATURAL debe tener nombre y apellido");
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else if (tipoClinte.equals("JURIDICA") && (cliente.getRazon_social().isEmpty() || cliente.getAno_fundacion() == null)) {
            response.setMetadata("Respuesta nok", "-1", "Tipo de cliente JURIDICA debe tener razón social y año fundación");
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
        } else {
            try {
                //Buscar cliente a actualizar
                Optional<Cliente> clienteSearch = clienteDao.findById(id);
                if (clienteSearch.isPresent()) {
                    //Se actualiza el cliente
                    clienteSearch.get().setDocumento(cliente.getDocumento());
                    clienteSearch.get().setRut(cliente.getRut());
                    clienteSearch.get().setNombre(cliente.getNombre());
                    clienteSearch.get().setApellido(cliente.getApellido());
                    clienteSearch.get().setRazon_social(cliente.getRazon_social());
                    clienteSearch.get().setAno_fundacion(cliente.getAno_fundacion());
                    clienteSearch.get().setTipo_documento(cliente.getTipo_documento());
                    clienteSearch.get().setTipo_cliente(cliente.getTipo_cliente());
                    Cliente clienteSaved = clienteDao.save(clienteSearch.get());
                    if (clienteSaved != null) {
                        List<Cliente> list = new ArrayList<>();
                        list.add(clienteSaved);
                        response.setCliente(list);
                        response.setMetadata("Respuesta ok", "00", "Cliente actualizado correctamente");
                    } else {
                        response.setMetadata("Respuesta nok", "-1", "Cliente no actualizado");
                        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    response.setMetadata("Respuesta nok", "-1", "No se encontró el cliente a actualizar");
                    return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.NOT_FOUND);
                }
            } catch (Exception e){
                e.getStackTrace();
                response.setMetadata("Respuesta nok", "-1", "Error al actualizar cliente");
                return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ClienteResponseRest> delete(Long id) {
        ClienteResponseRest response = new ClienteResponseRest();
        try {
            clienteDao.deleteById(id);
            response.setMetadata("Respuesta OK", "00", "Se eliminó el cliente");
        } catch (Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta nok", "-1", "Error al eliminar cliente");
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }
}
