package com.concessionaria.projetocarros.dtos;

import com.concessionaria.projetocarros.models.Marca;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ModeloDTO {

    @NotBlank(message = "O campo modelo está vazio")
    private String nomeModelo;

    @NotNull(message = "Defina uma marca")
    private Marca marca;

    public String getNomeModelo() {
        return nomeModelo;
    }

    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
