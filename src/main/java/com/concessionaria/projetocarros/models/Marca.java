package com.concessionaria.projetocarros.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;

    @OneToMany
    private List<Modelo> modelos;

    public Marca(Long id, String nome) {
        super();
        this.id = id;
        this.nome = nome;
    }

    public Marca(Marca marca) {

    }
    public Marca() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
