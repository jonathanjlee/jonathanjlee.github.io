/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.data;

import com.sg.bullsandcows.models.Guess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
public class BACGuessDBDao implements BACGuessDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public BACGuessDBDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Guess addGuess(String userGuess) {
        LocalDateTime time = LocalDateTime.now();
        Guess guess = new Guess(userGuess, time);
        final String ADD_GUESS = "INSERT INTO Guess(guess, time) VALUES (?,?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(ADD_GUESS,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, userGuess);
            statement.setTimestamp(2, Timestamp.valueOf(time));
            return statement;
        }, keyHolder);

        guess.setGuessId(keyHolder.getKey().intValue());

        return guess;
    }

    @Override
    public List<Guess> getAllGuesses() {
        final String GET_ALL_GUESSES = "SELECT * FROM Guess";
        return jdbc.query(GET_ALL_GUESSES, new guessMapper());
    }

    @Override
    @Transactional
    public void deleteGuessById(int guessId) {
        final String DELETE_GUESS_FROM_ROUND 
                = "DELETE FROM Round "
                + "WHERE GuessId = ?;";
        jdbc.update(DELETE_GUESS_FROM_ROUND, guessId);
        final String DELETE_GUESS_BY_ID
                = "DELETE FROM Guess "
                + "WHERE GuessId = ?;";
        jdbc.update(DELETE_GUESS_BY_ID, guessId);
    }

    public static final class guessMapper implements RowMapper<Guess> {

        @Override
        public Guess mapRow(ResultSet rs, int i) throws SQLException {
            Guess guess = new Guess();
            guess.setGuessId(rs.getInt("GuessId"));
            guess.setGuess(rs.getString("Guess"));
            guess.setTime(rs.getTimestamp("Time").toLocalDateTime());
            return guess;
        }

    }
}
