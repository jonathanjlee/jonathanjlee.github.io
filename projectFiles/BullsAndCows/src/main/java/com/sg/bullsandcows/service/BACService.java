/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.service;

import com.sg.bullsandcows.models.Game;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jonat
 */
@Repository
public interface BACService {
    
    /**
     * Create a list comprised of random 4 digits with no repeating numbers.
     * @return - A list of 4 non-repeating digits for the player to guess.
     */
    public String createAnswer();
    
    /**
     * Iterates through a list of games to check which ones are still active. 
     * If a game is still active, the method will hide the system-generated 
     * answer.
     * @param allGames - A list of games whose statuses we are checking.
     * @return - The list of games with the answers visible (if not in progress) 
     * or hidden (if in progress).
     */
    public List<Game> hideListAnswers(List<Game> allGames);
    
    /**
     * Checks to see if the game is still active. If it is, the method will 
     * hide the system-generated answer.
     * @param game - The game whose status we are checking
     * @return - The game with the answer visible (if not in progress) or 
     * hidden (if in progress).
     */
    public Game hideAnswer(Game game);
    
    /**
     * Checks to make sure the user's input is a valid string of numbers.
     * @param userNumber - The 4 digit number that the user entered.
     * @return - A value of 'true' if the number is valid.
     */
    public Boolean validateGuess(String userNumber);
    
    /**
     * Creates a map with the keys 'e' (exactMatch) and 'p' (partialMatch) with 
     * their corresponding values once the userGuess has been scored.
     * @param answer - The answer that was created at the beginning of the game.
     * @param userNumber - The user's guest that will be iterated through to 
     * arrive with the score.
     * @return - A map with the amount of points for exactMatches and 
     * partialMatches.
     */
    public Map<String, Integer> scoreGuess(String answer, String userNumber);
    
    /**
     * Checks to see if a Round has 4 exact matches.If it does, it will flip 
     * the inProgress property to false.
     * @param game - The current game.
     * @param score - The HashMap containing the score for the round.
     * @return Game - The current Game.
     */
    public Game completeGame(Game game, Map<String, Integer> score);
}

