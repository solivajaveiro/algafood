package com.soliva.algafood.infrastructure.repository;

import com.soliva.algafood.domain.model.Restaurante;
import com.soliva.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);// From Restaurante

        Predicate nomePredicate = builder.like(root.get("nome"), "%" + nome + "%");

        Predicate taxaInicialPredicate = builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);

        Predicate taxaFinalPredicate = builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);

        criteria.where(nomePredicate, taxaInicialPredicate, taxaFinalPredicate);

        TypedQuery<Restaurante> query = manager.createQuery(criteria);
        return query.getResultList();
    }

//    @Override
//    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
//
//        var jpql = new StringBuilder();
//        jpql.append("from Restaurante where 0 = 0 ");
//
//        var parametros = new HashMap<String, Object>();
//
//        if(StringUtils.hasLength(nome)) {
//            jpql.append("and nome like :nome ");
//            parametros.put("nome", "%" + nome + "%");
//        }
//
//        if(taxaFreteInicial != null) {
//            jpql.append("and taxaFrete >= :taxaInicial ");
//            parametros.put("taxaInicial", taxaFreteInicial);
//        }
//
//        if(taxaFreteFinal != null) {
//            jpql.append("and taxaFrete <= :taxaFinal ");
//            parametros.put("taxaFinal", taxaFreteFinal);
//        }
//
//       TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);
//
//        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
//        return query.getResultList();
//    }
}