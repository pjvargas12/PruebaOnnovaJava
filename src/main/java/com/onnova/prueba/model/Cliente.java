package com.onnova.prueba.model;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "T_CLIENTE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "documento", nullable = false, length = 11)
    private Long documento;

    @Column(name = "nombre", length = 80)
    private String nombre;

    @Column(name = "apellido", length = 250)
    private String apellido;

    @Column(name = "rut", nullable = false, length = 12)
    private Long rut;

    @Column(name = "razon_social", length = 100)
    private String razon_social;

    @Column(name = "ano_fundacion", length = 5)
    private Integer ano_fundacion;

    @Column(name = "tipo_documento", length = 30)
    private String tipo_documento;

    @Column(name = "tipo_cliente", length = 20)
    private String tipo_cliente;
}
