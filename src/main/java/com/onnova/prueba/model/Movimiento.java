package com.onnova.prueba.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "T_MOVIMIENTO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "valor", length = 10)
    private Long valor;

    @Column(name = "fecha")
    private Date fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuenta_remitente")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cuenta cuenta_remitente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuenta_receptora")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cuenta cuenta_receptora;

}
