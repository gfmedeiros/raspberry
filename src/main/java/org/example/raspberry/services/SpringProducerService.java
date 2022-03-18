package org.example.raspberry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringProducerService implements ProducerService {

    private final JdbcTemplate template;

    @Autowired
    public SpringProducerService(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<ConsecutiveWins> findShortestConsecutiveWins() {
        return template.query("\n" +
                "SELECT *\n" +
                "  FROM (\n" +
                "SELECT producer, \n" +
                "       LEAD(year, 1) OVER (PARTITION BY producer ORDER BY year) - year AS intervalo,\n" +
                "       year AS previousWin, \n" +
                "       LEAD(year, 1) OVER (PARTITION BY producer ORDER BY year) AS followingWin\n" +
                "  FROM nominees\n" +
                " WHERE winner = TRUE\n" +
                ") WHERE intervalo =  (SELECT MIN(intervalo)\n" +
                " FROM (\n" +
                "SELECT LEAD(year, 1) OVER (PARTITION BY producer ORDER BY year) - year AS intervalo,\n" +
                "  FROM nominees\n" +
                " WHERE winner = TRUE\n" +
                "))", new ConsecutiveWinsMapper());
    }

    @Override
    public List<ConsecutiveWins> findLongestConsecutiveWins() {
        return template.query("SELECT *\n" +
                "  FROM (\n" +
                "SELECT producer, \n" +
                "       LEAD(year, 1) OVER (PARTITION BY producer ORDER BY year) - year AS intervalo,\n" +
                "       year AS previousWin, \n" +
                "       LEAD(year, 1) OVER (PARTITION BY producer ORDER BY year) AS followingWin\n" +
                "  FROM nominees\n" +
                " WHERE winner = TRUE\n" +
                ") WHERE intervalo =  (SELECT MAX(intervalo)\n" +
                " FROM (\n" +
                "SELECT LEAD(year, 1) OVER (PARTITION BY producer ORDER BY year) - year AS intervalo,\n" +
                "  FROM nominees\n" +
                " WHERE winner = TRUE\n" +
                "))", new ConsecutiveWinsMapper());
    }
}
