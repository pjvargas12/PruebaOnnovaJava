package com.onnova.prueba.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "T_CUENTA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "numero", nullable = false, length = 20)
    private Long numero;

    @Column(name = "saldo", length = 10)
    private Long saldo;

    @Column(name = "tipo_moneda", length = 30)
    private String tipo_moneda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cliente cliente;

}
