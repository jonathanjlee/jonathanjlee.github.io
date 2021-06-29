/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.data;

import com.sg.bullsandcows.models.Game;
import com.sg.bullsandcows.models.Guess;
import com.sg.bullsandcows.models.Round;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author jonat
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BACGameDBDaoTest {

    @Autowired
    BACGameDao gameDao;

    @Autowired
    BACRoundDao roundDao;

    @Autowired
    BACGuessDao guessDao;

    public BACGameDBDaoTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            gameDao.deleteGameById(game.getGameId());
        }

        List<Round> rounds = roundDao.getAllRounds();
        for (Round round : rounds) {
            roundDao.deleteRoundById(round.getRoundId());
        }

        List<Guess> guesses = guessDao.getAllGuesses();
        for (Guess guess : guesses) {
            guessDao.deleteGuessById(guess.getGuessId());
        }
    }

    @Test
    public void testAddGetGame() {
        Game game = new Game();
        game.setAnswer("0123");
        game.setInProgress(Boolean.TRUE);
        game.setRounds(null);
        game = gameDao.addGame(game.getAnswer());

        Game fromDatabase = gameDao.getGame(game.getGameId());

        assertEquals(game, fromDatabase);

    }

    @Test
    public void testAddGetAllGames() {
        Game game1 = new Game();
        game1.setAnswer("0123");
        game1.setInProgress(Boolean.TRUE);
        game1.setRounds(null);
        game1 = gameDao.addGame(game1.getAnswer());

        Game game2 = new Game();
        game2.setAnswer("4567");
        game2.setInProgress(Boolean.TRUE);
        game2.setRounds(null);
        game2 = gameDao.addGame(game2.getAnswer());

        List<Game> allGames = gameDao.getAllGames();

        assertEquals(2, allGames.size());
        assertTrue(allGames.contains(game1));
        assertTrue(allGames.contains(game2));
    }

    @Test
    public void testUpdateGame() {
        Game game = new Game();
        game.setAnswer("0123");
        game.setInProgress(Boolean.TRUE);
        game.setRounds(null);
        game = gameDao.addGame(game.getAnswer());

        Game fromDatabase = gameDao.getGame(game.getGameId());

        assertEquals(game, fromDatabase);

        game.setInProgress(Boolean.FALSE);
        gameDao.updateGame(game);

        assertNotEquals(game, fromDatabase);

        fromDatabase = gameDao.getGame(game.getGameId());

        assertEquals(game, fromDatabase);
    }

    @Test
    public void testDeleteGame() {
        Game game1 = new Game();
        game1.setAnswer("0123");
        game1.setInProgress(Boolean.TRUE);
        game1.setRounds(null);
        game1 = gameDao.addGame(game1.getAnswer());

        Guess guess1 = guessDao.addGuess("3456");

        Round round1 = new Round();
        round1.setGuessId(guess1.getGuessId());
        round1.setGameId(game1.getGameId());
        round1.setGuess(guess1.getGuess());
        round1.setTime(guess1.getTime().withNano(0));
        round1.setResult("e:0:p:0");
        round1 = roundDao.addRound(round1);

        Game game2 = new Game();
        game2.setAnswer("4283");
        game2.setInProgress(Boolean.TRUE);
        game2.setRounds(null);
        game2 = gameDao.addGame(game2.getAnswer());

        Guess guess2 = guessDao.addGuess("7890");

        Round round2 = new Round();
        round2.setGuessId(guess2.getGuessId());
        round2.setGameId(game2.getGameId());
        round2.setGuess(guess2.getGuess());
        round2.setTime(guess2.getTime().withNano(0));
        round2.setResult("e:1:p:1");
        round2 = roundDao.addRound(round2);

        List<Game> allGames = gameDao.getAllGames();

        assertEquals(2, allGames.size());

        gameDao.deleteGameById(game1.getGameId());

        allGames = gameDao.getAllGames();

        assertEquals(1, allGames.size());
        assertFalse(allGames.isEmpty());
    }
}
