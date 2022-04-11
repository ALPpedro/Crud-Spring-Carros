package com.concessionaria.projetocarros.dtos;

import com.concessionaria.projetocarros.models.Marca;
import com.concessionaria.projetocarros.models.Modelo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VeiculoDTO {

    @Min(value = 1, message = "Escolha um valor")
    private float valor;

    @NotNull(message = "Escolha um modelo")
    private Modelo modelo;

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
