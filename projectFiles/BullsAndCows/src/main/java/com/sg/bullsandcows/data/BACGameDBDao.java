/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.data;

import com.sg.bullsandcows.models.Game;
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
public class BACGameDBDao implements BACGameDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public BACGameDBDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @Transactional
    public Game addGame(String answer) {
        Game game = new Game();
        game.setAnswer(answer);
        game.setInProgress(Boolean.TRUE);
        final String INSERT_NEW_GAME
                = "INSERT INTO Game(Answer, InProgress) "
                + "VALUES (?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(INSERT_NEW_GAME,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, game.getAnswer());
            statement.setBoolean(2, game.getInProgress());
            return statement;
        }, keyHolder);

        game.setGameId(keyHolder.getKey().intValue());

        return game;
    }

    @Override
    public List<Game> getAllGames() {
        final String DISPLAY_ALL_GAMES
                = "SELECT * "
                + "FROM Game;";
        return jdbc.query(DISPLAY_ALL_GAMES, new gameMapper());
    }

    @Override
    public Game getGame(int gameId) {
        final String DISPLAY_A_GAME
                = "SELECT GameId, Answer, InProgress "
                + "FROM Game "
                + "WHERE GameId = ?;";
        return jdbc.queryForObject(DISPLAY_A_GAME, new gameMapper(), gameId);
    }

    @Override
    public void updateGame(Game game) {
        final String UPDATE_GAME
                = "UPDATE Game SET Answer = ?, InProgress = ? "
                + "WHERE GameId = ?;";
        jdbc.update(UPDATE_GAME, game.getAnswer(), game.getInProgress(),
                game.getGameId());
    }

    @Override
    @Transactional
    public void deleteGameById(int gameId) {
        final String DELETE_GAME_FROM_ROUND
                = "DELETE FROM Round "
                + "WHERE GameId = ?;";
        jdbc.update(DELETE_GAME_FROM_ROUND, gameId);
        final String DELETE_GAME_BY_ID
                = "DELETE FROM Game "
                + "WHERE GameId = ?;";
        jdbc.update(DELETE_GAME_BY_ID, gameId);
    }

    public static final class gameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("GameId"));
            game.setAnswer(rs.getString("Answer"));
            game.setInProgress(rs.getBoolean("InProgress"));
            return game;
        }
    }

}
