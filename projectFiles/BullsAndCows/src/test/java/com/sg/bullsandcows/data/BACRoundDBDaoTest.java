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
import static org.junit.Assert.*;
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
public class BACRoundDBDaoTest {

    @Autowired
    BACGameDao gameDao;

    @Autowired
    BACRoundDao roundDao;

    @Autowired
    BACGuessDao guessDao;

    public BACRoundDBDaoTest() {
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
    public void testAddGetRound() {
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
        
        Round fromDatabase = roundDao.getRound(round1.getRoundId());
        
        assertEquals(round1.getRoundId(), fromDatabase.getRoundId());
        assertEquals(round1.getGuessId(), fromDatabase.getGuessId());
        assertEquals(round1.getGameId(), fromDatabase.getGameId());
        assertEquals(round1.getGuess(), fromDatabase.getGuess());
        assertEquals(round1.getResult(), fromDatabase.getResult());
    }

    @Test
    public void testGetAllRoundsAndDelete() {
        Game game = new Game();
        game.setAnswer("0123");
        game.setInProgress(Boolean.TRUE);
        game.setRounds(null);
        game = gameDao.addGame(game.getAnswer());
        
        Guess guess1 = guessDao.addGuess("4567");
        Guess guess2 = guessDao.addGuess("8901");
        
        Round round1 = new Round();
        round1.setGuessId(guess1.getGuessId());
        round1.setGameId(game.getGameId());
        round1.setGuess(guess1.getGuess());
        round1.setTime(guess1.getTime().withNano(0));
        round1.setResult("e:0:p:0");
        round1 = roundDao.addRound(round1);
        
        Round round2 = new Round();
        round2.setGuessId(guess2.getGuessId());
        round2.setGameId(game.getGameId());
        round2.setGuess(guess2.getGuess());
        round2.setTime(guess2.getTime().withNano(0));
        round2.setResult("e:0:p:0");
        round2 = roundDao.addRound(round2);
        
        List<Round> allRounds = roundDao.getAllRounds();
        
        assertEquals(2, allRounds.size());
        assertTrue(allRounds.contains(round1));
        assertTrue(allRounds.contains(round2));
        
        roundDao.deleteRoundById(round2.getRoundId());
        
        allRounds = roundDao.getAllRounds();
        
        assertEquals(1, allRounds.size());
        assertTrue(allRounds.contains(round1));
        assertFalse(allRounds.contains(round2));
    }
    
    @Test
    public void testGetAllRoundsByGameId() {
        Game game = new Game();
        game.setAnswer("0123");
        game.setInProgress(Boolean.TRUE);
        game.setRounds(null);
        game = gameDao.addGame(game.getAnswer());
        
        Guess guess1 = guessDao.addGuess("4567");
        Guess guess2 = guessDao.addGuess("8901");
        
        Round round1 = new Round();
        round1.setGuessId(guess1.getGuessId());
        round1.setGameId(game.getGameId());
        round1.setGuess(guess1.getGuess());
        round1.setTime(guess1.getTime().withNano(0));
        round1.setResult("e:0:p:0");
        round1 = roundDao.addRound(round1);
        
        Round round2 = new Round();
        round2.setGuessId(guess2.getGuessId());
        round2.setGameId(game.getGameId());
        round2.setGuess(guess2.getGuess());
        round2.setTime(guess2.getTime().withNano(0));
        round2.setResult("e:0:p:0");
        round2 = roundDao.addRound(round2);
        
        List<Game> allRounds = roundDao.getAllRoundsByGameId(game.getGameId());
        Game fullGame = allRounds.get(0);
        
        assertEquals(2, fullGame.getRounds().size());
        assertTrue(fullGame.getRounds().contains(round1));
        assertTrue(fullGame.getRounds().contains(round2));
    }

}
