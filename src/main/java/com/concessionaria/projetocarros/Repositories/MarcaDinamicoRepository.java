package com.concessionaria.projetocarros.Repositories;

import com.concessionaria.projetocarros.models.resposta.ModeloResposta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
public class MarcaDinamicoRepository {

    private final EntityManager em;

    public MarcaDinamicoRepository(EntityManager em) {
        this.em = em;
    }

    public Page find1(String nome, Pageable pageable) {

        String query = "select M from Marca as M";
        String countQ = "Select Count(id) from Marca as m ";
        String condicao = " WHERE";

        if (nome != null) {
            query += condicao + " M.nome LIKE :nome";
            countQ += condicao + " m.nome LIKE :nome";
        }

        var q = em.createQuery(query);
        var countQuery = em.createQuery(countQ);

        if (nome != null) {
            q.setParameter("nome", "%" + nome + "%");
            countQuery.setParameter("nome", "%" + nome + "%");
        }

        q.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());
        List modeloss = q.getResultList();

        Long countResults = (Long) countQuery.getSingleResult();
        Page<ModeloResposta> model = new PageImpl(modeloss, pageable, countResults);
        return model;
    }
}

