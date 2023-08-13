package app.statistics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class StatisticRepo {

    @PersistenceContext
    EntityManager entityManager;

    public List<StatisticGrupResult> groupByPropertyWithConditions(String categorie) {

        List<StatisticGrupResult> statistics = new ArrayList<>();

        String sqlQuery = ""+
        "SELECT grupa, COUNT(grupa) AS valoare FROM (" +
        "SELECT " +
        "    CASE " +
        "        WHEN :categorie = 'categorieAnimal' THEN C.numeCategorie " +
        "        WHEN :categorie = 'rasa' THEN R.numeRasa " +
        "        ELSE TIMESTAMPDIFF(YEAR, A.dataNastere, CURDATE()) " +
        "    END AS grupa " +
        "FROM Animal A " +
        "JOIN Rasa_Animal R ON A.rasa_id = R.id " +
        "JOIN Categorie_Animal C ON R.categorieAnimal_id = C.id " +
        ") AS AnimalExtins " +
        "GROUP BY grupa";

        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("categorie", categorie);

        List<Object[]> results = query.getResultList();

        for (Object[] o : results) {
            //statistics.add(new StatisticGrupResult((String) o[0], (long) o[1]));
        }

        return statistics;
    }
}
