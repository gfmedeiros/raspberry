package org.example.raspberry.services;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsecutiveWinsMapper implements RowMapper<ConsecutiveWins> {
    @Override
    public ConsecutiveWins mapRow(ResultSet rs, int rowNum) throws SQLException {
        ConsecutiveWins consecutiveWins = new ConsecutiveWins();
        consecutiveWins.setProducer(rs.getString("producer"));
        consecutiveWins.setInterval(rs.getInt("intervalo"));
        consecutiveWins.setPreviousWin(rs.getInt("previousWin"));
        consecutiveWins.setFollowingWin(rs.getInt("followingWin"));
        return consecutiveWins;
    }
}
