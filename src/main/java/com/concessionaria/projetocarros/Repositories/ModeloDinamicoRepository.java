package com.concessionaria.projetocarros.Repositories;
import com.concessionaria.projetocarros.models.Modelo;
import com.concessionaria.projetocarros.models.resposta.ModeloResposta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ModeloDinamicoRepository {
    private final EntityManager em;

    public ModeloDinamicoRepository(EntityManager em) {
        super();
        this.em = em;
    }

    public Page find1(String nome,Long idMarca, Pageable pageable){

        String query = "select M from Modelo as M";
        String countQ = "Select Count(id) from Modelo as m ";
        String condicao = " WHERE";

        if (idMarca != null){
            query += condicao+" M.marca.id = :id";
            countQ += condicao+" m.id = :id";
            condicao =" AND ";
        }
        if (nome != null){
            query += condicao+" M.nomeModelo LIKE :nome";
            countQ += condicao+" m.nomeModelo LIKE :nome";
            condicao = " and ";
        }

        var q = em.createQuery(query);
        var countQuery = em.createQuery(countQ);


        if (idMarca != null){
            q.setParameter("id", idMarca);
            countQuery.setParameter("id", idMarca);
        }
        if (nome != null){
            q.setParameter("nome", "%"+nome+"%");
            countQuery.setParameter("nome", "%"+nome+"%");
        }

        q.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());
        List modeloss = q.getResultList();

        Long countResults = (Long) countQuery.getSingleResult();
        Page <ModeloResposta> model = new PageImpl(modeloss, pageable, countResults);
        return model;

    }
}
