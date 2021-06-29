/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.data;

import com.sg.bullsandcows.data.BACGameDBDao.gameMapper;
import com.sg.bullsandcows.models.Game;
import com.sg.bullsandcows.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jonat
 */
@Repository
public class BACRoundDBDao implements BACRoundDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public BACRoundDBDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @Transactional
    public Round addRound(Round round) {
        final String INSERT_NEW_ROUND
                = "INSERT INTO Round(GameId, GuessId, Result) "
                + "VALUES (?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update((Connection conn) -> {
            PreparedStatement statement
                    = conn.prepareStatement(INSERT_NEW_ROUND,
                            Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, round.getGameId());
            statement.setInt(2, round.getGuessId());
            statement.setString(3, round.getResult());
            return statement;
        }, keyHolder);
        round.setRoundId(keyHolder.getKey().intValue());
        return round;
    }

    @Override
    public Round getRound(int roundId) {
        final String GET_A_ROUND = "SELECT * "
                + "FROM Round r "
                + "INNER JOIN Guess g ON r.GuessId = g.GuessId "
                + "WHERE RoundId = ?;";
        return jdbc.queryForObject(GET_A_ROUND, new roundMapper(), roundId);
    }

    @Override
    public List<Round> getAllRounds() {
        final String GET_ALL_ROUNDS
                = "SELECT * "
                + "FROM Round r "
                + "INNER JOIN Guess g ON r.GuessId = g.GuessId;";
        return jdbc.query(GET_ALL_ROUNDS, new roundMapper());
    }

    @Override
    public List<Game> getAllRoundsByGameId(int gameId) {
        final String GET_ALL_ROUNDS_BY_GAMEID
                = "SELECT * "
                + "FROM Game "
                + "WHERE GameId = ?;";
        List<Game> rounds = jdbc.query(GET_ALL_ROUNDS_BY_GAMEID,
                new gameMapper(), gameId);
        addRoundsForGame(rounds);
        return rounds;
    }

    @Override
    @Transactional
    public void deleteRoundById(int roundId) {
        final String DELETE_ROUND_BY_ID
                = "DELETE FROM Round r "
                + "WHERE RoundId = ?;";
        jdbc.update(DELETE_ROUND_BY_ID, roundId);
    }

    public static final class roundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("RoundId"));
            round.setGuessId(rs.getInt("GuessId"));
            round.setGameId(rs.getInt("GameId"));
            round.setGuess(rs.getString("Guess"));
            round.setTime(rs.getTimestamp("Time").toLocalDateTime().withNano(0));
            round.setResult(rs.getString("Result"));
            return round;
        }
    }

    private void addRoundsForGame(List<Game> games) {
        for (Game game : games) {
            game.setRounds(getRoundForGame(game));
        }
    }

    private List<Round> getRoundForGame(Game game) {
        final String SELECT_ROUND_FOR_GAME
                = "SELECT * "
                + "FROM Round r "
                + "INNER JOIN Guess g ON r.GuessId = g.GuessId "
                + "WHERE r.GameId = ? "
                + "ORDER BY Time DESC;";
        return jdbc.query(SELECT_ROUND_FOR_GAME, new roundMapper(),
                game.getGameId());
    }
}
