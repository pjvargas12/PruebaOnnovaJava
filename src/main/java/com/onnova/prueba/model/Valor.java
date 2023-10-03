package com.onnova.prueba.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "T_VALOR")
public class Valor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String abreviacion;
    private String nombre;
    private String descripcion;
    private String vigente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tvf")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TipoValor tipoValor;
}
