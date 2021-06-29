/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.data;

import com.sg.bullsandcows.models.Game;
import java.util.List;

/**
 *
 * @author jonat
 */
public interface BACGameDao {
    
    /**
     * Starts a new game using the answer that was created in the service layer.
     * @param answer - The list of 4 random digits that was created in the 
     * service layer.
     * @return - A new Game that is created by the database.
     */
    public Game addGame(String answer);
    
    /**
     * Retrieves a list of all games in the database.
     * @return - A list of all games in the database.
     */
    public List<Game> getAllGames();
    
    /**
     * Retrieves a game from the database based on the user provided gameId.
     * @param gameId
     * @return - The game that corresponds to the user provided gameId.
     */
    public Game getGame(int gameId);
    
    /**
     * Updates the database with any changes to the Game.
     * @param game - The Game to update.
     */
    public void updateGame(Game game);
    
    /**
     * 
     * @param gameId 
     */
    public void deleteGameById(int gameId);
    
    /**
     * Pulls all the rounds and their associated guesses that are affiliated 
     * with a given gameId.
     * @param gameId - The requested gameId.
     * @return - A list of Games and their rounds sorted by chronological order.
     */
//    public List<Game> getGamesByGameId(int gameId);
}
