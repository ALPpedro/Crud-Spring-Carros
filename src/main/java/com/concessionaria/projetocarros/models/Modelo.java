package com.concessionaria.projetocarros.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomeModelo")
    private String nomeModelo;

    @ManyToOne
    private Marca marca;

    @OneToMany
    private List<Veiculo> veiculos;

    public Modelo(Modelo modelo) {
    }

    public Modelo(Long id, String nomeModelo, Marca marca) {
        super();
        this.id = id;
        this.nomeModelo = nomeModelo;
        this.marca = marca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
