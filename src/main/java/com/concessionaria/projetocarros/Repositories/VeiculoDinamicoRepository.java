package com.concessionaria.projetocarros.Repositories;

import com.concessionaria.projetocarros.models.resposta.ModeloResposta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class VeiculoDinamicoRepository {

    private final EntityManager em;

    public VeiculoDinamicoRepository(EntityManager em) {
        this.em = em;
    }

    public Page find1(Long idMarca,Long idModelo, float valorMenor, float valorMaior, Pageable pageable){

        String query = "select V from Veiculo as V";
        String countQ = "Select Count(id) from Veiculo as v ";
        String condicao = " WHERE";

        if (idMarca != null){
            query += condicao+" V.modelo.marca.id = :idMarca";
            countQ += condicao+" v.modelo.marca.id = :idMarca";
            condicao =" AND ";
        }
        if (idModelo != null){
            query += condicao+" V.modelo.id = :idModelo";
            countQ += condicao+" v.modelo.id = :idModelo";
            condicao =" AND ";
        }
        if (valorMenor != 0){
            query += condicao+" valor > :valorMenor";
            countQ += condicao+" valor > :valorMenor";
            condicao = " and ";
        }

        if (valorMaior != 0){
            query += condicao+" valor < :valorMaior";
            countQ += condicao+" valor < :valorMaior";
            condicao = " and ";
        }

        var q = em.createQuery(query);
        var countQuery = em.createQuery(countQ);


        if (idMarca != null){
            q.setParameter("idMarca", idMarca);
            countQuery.setParameter("idMarca", idMarca);
        }
        if (idModelo != null){
            q.setParameter("idModelo", idModelo);
            countQuery.setParameter("idModelo", idModelo);
        }
        if (valorMaior != 0){
            q.setParameter("valorMaior", valorMaior);
            countQuery.setParameter("valorMaior", valorMaior);
        }
        if (valorMenor != 0){
            q.setParameter("valorMenor", valorMenor);
            countQuery.setParameter("valorMenor", valorMenor);
        }

        q.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());
        List modeloss = q.getResultList();

        Long countResults = (Long) countQuery.getSingleResult();
        return new PageImpl(modeloss, pageable, countResults);

    }
}
