/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.service;

import com.sg.bullsandcows.data.BACGameDao;
import com.sg.bullsandcows.data.BACGuessDao;
import com.sg.bullsandcows.data.BACRoundDao;
import com.sg.bullsandcows.models.Game;
import com.sg.bullsandcows.models.Guess;
import com.sg.bullsandcows.models.Round;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class BACServiceImplTest {

    @Autowired
    BACGameDao gameDao;

    @Autowired
    BACRoundDao roundDao;

    @Autowired
    BACGuessDao guessDao;

    @Autowired
    BACServiceImpl service;

    public BACServiceImplTest() {
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
    public void testCreateAnswer() {
        String answer = "";
        answer = service.createAnswer();

        assertNotEquals("", answer);
    }

    @Test
    public void testHideListAnswer() {
        Game game1 = gameDao.addGame("0123");
        Game game2 = gameDao.addGame("4567");

        List<Game> allGames = gameDao.getAllGames();

        assertEquals(2, allGames.size());
        assertEquals("0123", game1.getAnswer());
        assertEquals("4567", game2.getAnswer());

        Map<String, Integer> score = new HashMap<>();
        score.put("e", 4);
        score.put("p", 0);
        service.completeGame(game2, score);
        allGames.set(1, game2);
        allGames = service.hideListAnswers(allGames);
        game1 = allGames.get(0);
        game2 = allGames.get(1);

        assertEquals(2, allGames.size());
        assertEquals(true, game1.getInProgress());
        assertEquals("<<hidden>>", game1.getAnswer());
        assertEquals(false, game2.getInProgress());
        assertEquals("4567", game2.getAnswer());
    }

    @Test
    public void testValidateGuess() {
        String perfectGuess = "1234";
        String insufficientDigits = "123";
        String tooManyDigits = "12345";
        String repeatingDigits = "1232";
        String text = "apple";

        Boolean valid = service.validateGuess(perfectGuess);
        assertEquals(true, valid);

        valid = service.validateGuess(insufficientDigits);
        assertEquals(false, valid);

        valid = service.validateGuess(tooManyDigits);
        assertEquals(false, valid);

        valid = service.validateGuess(repeatingDigits);
        assertEquals(false, valid);

        valid = service.validateGuess(text);
        assertEquals(false, valid);
    }

    @Test
    public void testScoring() {
        String answer = "1234";
        String perfectMatch = "1234";
        String noMatches = "5678";
        String onePartialMatch = "5671";
        String oneExactOnePartialMatch = "1378";
        String oneExactMatch = "1678";
        Map<String, Integer> score;
        int exactMatch;
        int partialMatch;

        score = service.scoreGuess(answer, perfectMatch);
        exactMatch = score.get("e");
        partialMatch = score.get("p");
        assertEquals(4, exactMatch);
        assertEquals(0, partialMatch);

        score = service.scoreGuess(answer, noMatches);
        exactMatch = score.get("e");
        partialMatch = score.get("p");
        assertEquals(0, exactMatch);
        assertEquals(0, partialMatch);

        score = service.scoreGuess(answer, onePartialMatch);
        exactMatch = score.get("e");
        partialMatch = score.get("p");
        assertEquals(0, exactMatch);
        assertEquals(1, partialMatch);

        score = service.scoreGuess(answer, oneExactOnePartialMatch);
        exactMatch = score.get("e");
        partialMatch = score.get("p");
        assertEquals(1, exactMatch);
        assertEquals(1, partialMatch);

        score = service.scoreGuess(answer, oneExactMatch);
        exactMatch = score.get("e");
        partialMatch = score.get("p");
        assertEquals(1, exactMatch);
        assertEquals(0, partialMatch);
    }

    @Test
    public void testCompleteGameAndHideAnswer() {
        Game game = gameDao.addGame("0123");

        assertEquals(true, game.getInProgress());
        assertEquals("0123", game.getAnswer());

        Map<String, Integer> score = new HashMap<>();
        score.put("e", 4);
        score.put("p", 0);

        service.completeGame(game, score);

        assertEquals(false, game.getInProgress());

        game = gameDao.getGame(game.getGameId());
        service.hideAnswer(game);

        assertEquals("<<hidden>>", game.getAnswer());
    }

}
