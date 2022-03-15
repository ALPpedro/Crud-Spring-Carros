package com.concessionaria.projetocarros.dtos;

import javax.validation.constraints.NotBlank;

public class MarcaDTO {

    @NotBlank(message = "O campo nome está vazio")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
