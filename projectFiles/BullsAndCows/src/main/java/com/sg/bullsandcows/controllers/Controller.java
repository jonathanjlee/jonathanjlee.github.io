/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.controllers;

import com.sg.bullsandcows.data.BACGameDao;
import com.sg.bullsandcows.data.BACGuessDao;
import com.sg.bullsandcows.data.BACRoundDao;
import com.sg.bullsandcows.models.Game;
import com.sg.bullsandcows.models.GameAndGuessRequest;
import com.sg.bullsandcows.models.Guess;
import com.sg.bullsandcows.models.Round;
import com.sg.bullsandcows.service.BACService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jonat
 */
@RestController
@RequestMapping("/api/game")
public class Controller {

    private final BACGameDao gameDao;
    private final BACRoundDao roundDao;
    private final BACGuessDao guessDao;
    private final BACService service;

    public Controller(BACGameDao gameDao, BACRoundDao roundDao,
            BACGuessDao guessDao, BACService service) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
        this.guessDao = guessDao;
        this.service = service;
    }

    @PostMapping("/begin")
    public ResponseEntity<Game> beginGame() {
        Game newGame = gameDao.addGame(service.createAnswer());
        if (newGame == null) {
            return new ResponseEntity("Could not create a game. Try again.",
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity("GameId = " + newGame.getGameId(),
                HttpStatus.CREATED);
    }

    @PostMapping("/guess")
    public ResponseEntity<Round> startRound(@RequestBody GameAndGuessRequest gameAndGuess) {
        // Split the information from the GameAndGuessRequest object into the 
        // necessary object references and create the variables.
        Game currentGame;
        try {
            currentGame = gameDao.getGame(gameAndGuess.getGameId());
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity("Sorry, that game ID does not exist.",
                    HttpStatus.NOT_FOUND);
        }
        Guess userGuess;
        Map<String, Integer> score = new HashMap<>();
        // Check to see if the user's guess is a valid guess by running through 
        // a Pattern. If it passes, add it to the database with a timestamp.
        if (service.validateGuess(gameAndGuess.getUserNumber()) == false) {
            return new ResponseEntity("Please provide a non-repeating 4 "
                    + "digit number.", HttpStatus.NOT_ACCEPTABLE);
        } else {
            userGuess = guessDao.addGuess(gameAndGuess.getUserNumber());
        }
        // Score the user's answer and insert results into a HashMap.
        score = service.scoreGuess(currentGame.getAnswer(),
                userGuess.getGuess());
        // Create a record for the currentRound.
        Round currentRound = roundDao.addRound(createRound(
                currentGame.getGameId(), userGuess, score));
        // Once the exactMatches = 0, flip the inProgress Boolean and update 
        // the game in the database.
        gameDao.updateGame(service.completeGame(gameDao.getGame(
                currentGame.getGameId()), score));
        return ResponseEntity.ok(currentRound);
    }

    @GetMapping("/game")
    public List<Game> getAllGames() {
        return service.hideListAnswers(gameDao.getAllGames());
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> getGame(@PathVariable int gameId) {
        Game requestedGame;
        try {
            requestedGame = gameDao.getGame(gameId);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity("Sorry, that game ID does not exist.",
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(service.hideAnswer(requestedGame));
    }

    @GetMapping("/rounds/{gameId}")
    public List<Game> getAllRounds(@PathVariable int gameId) {
        return service.hideListAnswers(roundDao.getAllRoundsByGameId(gameId));
    }

    private Round createRound(int gameId, Guess userGuess,
            Map<String, Integer> score) {
        Round currentRound = new Round();
        currentRound.setGuessId(userGuess.getGuessId());
        currentRound.setGameId(gameId);
        currentRound.setGuess(userGuess.getGuess());
        currentRound.setTime(userGuess.getTime().withNano(0));
        currentRound.setResult("e:" + score.get("e") + ":p:" + score.get("p"));
        return currentRound;
    }

}
