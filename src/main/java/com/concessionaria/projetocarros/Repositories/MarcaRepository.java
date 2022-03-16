package com.concessionaria.projetocarros.Repositories;

import com.concessionaria.projetocarros.models.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Page<Marca> findAll(Pageable sort);
}
