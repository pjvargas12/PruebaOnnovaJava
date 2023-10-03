package com.onnova.prueba.response;

import com.onnova.prueba.model.Cliente;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClienteResponseRest extends ResponseRest {
    private List<Cliente> cliente;
}
