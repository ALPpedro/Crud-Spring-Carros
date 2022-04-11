package com.concessionaria.projetocarros.Repositories;

import com.concessionaria.projetocarros.models.Modelo;
import com.concessionaria.projetocarros.models.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    Page<Veiculo> findAll(Pageable sort);
}
