package com.concessionaria.projetocarros.Repositories;

import com.concessionaria.projetocarros.models.Modelo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    Page<Modelo> findAll(Pageable sort);
}
