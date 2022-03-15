package com.concessionaria.projetocarros.models;

import javax.persistence.*;

@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Modelo modelo;

    private int valor;

    public Veiculo(Long id, Modelo modelo, int valor) {
        super();
        this.id = id;
        this.modelo = modelo;
        this.valor = valor;
    }

    public Veiculo(Veiculo veiculo) {
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
