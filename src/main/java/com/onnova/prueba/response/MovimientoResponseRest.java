package com.onnova.prueba.response;

import com.onnova.prueba.model.Movimiento;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovimientoResponseRest extends ResponseRest {
    private List<Movimiento> movimiento;
}
