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
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
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
public class BACGuessDBDaoTest {

    @Autowired
    BACGameDao gameDao;

    @Autowired
    BACRoundDao roundDao;

    @Autowired
    BACGuessDao guessDao;

    public BACGuessDBDaoTest() {
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
    public void testAddGetAllAndDeleteGuess() {
        Guess guess1 = guessDao.addGuess("0123");
        Guess guess2 = guessDao.addGuess("4567");

        List<Guess> allGuesses = guessDao.getAllGuesses();

        assertEquals(2, allGuesses.size());
        assertEquals(guess1.getGuessId(), allGuesses.get(0).getGuessId());
        assertEquals(guess2.getGuessId(), allGuesses.get(1).getGuessId());

        guessDao.deleteGuessById(guess2.getGuessId());
        allGuesses = guessDao.getAllGuesses();

        assertEquals(1, allGuesses.size());
        assertEquals(guess1.getGuessId(), allGuesses.get(0).getGuessId());
    }

}
