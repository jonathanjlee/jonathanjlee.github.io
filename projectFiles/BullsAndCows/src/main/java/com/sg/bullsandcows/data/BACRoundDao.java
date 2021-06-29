/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.data;

import com.sg.bullsandcows.models.Game;
import com.sg.bullsandcows.models.Round;
import java.util.List;

/**
 *
 * @author jonat
 */
public interface BACRoundDao {
    
    /**
     * Adds the round to the database.
     * @param round
     * @return - The round that was added into the database.
     */
    public Round addRound(Round round);
    
    /**
     * Pulls a round based off of the provided roundId.
     * @param roundId - The unique ID assigned to the round.
     * @return - The round associated with the provided roundId.
     */
    public Round getRound(int roundId);
    
    public List<Game> getAllRoundsByGameId(int GameId);
    
    public List<Round> getAllRounds();
    
    public void deleteRoundById(int roundId);
}
