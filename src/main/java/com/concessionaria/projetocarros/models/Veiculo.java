package com.concessionaria.projetocarros.models;

import javax.persistence.*;

@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor")
    private float valor;

    @ManyToOne
    private Modelo modelo;



    public Veiculo(Long id, Modelo modelo, float valor) {
        super();
        this.id = id;
        this.modelo = modelo;
        this.valor = valor;
    }

    public Veiculo(Veiculo veiculo) {
    }

    public Veiculo() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
