package com.onnova.prueba.response;

import com.onnova.prueba.model.Cliente;
import com.onnova.prueba.model.Cuenta;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CuentaResponseRest extends ResponseRest {
    private List<Cuenta> cuenta;
}
