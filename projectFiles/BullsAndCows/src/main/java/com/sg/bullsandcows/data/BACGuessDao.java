/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.data;

import com.sg.bullsandcows.models.Guess;
import java.util.List;

/**
 *
 * @author jonat
 */
public interface BACGuessDao {
    
    /**
     * Adds a guess to the database with a timestamp
     */
    public Guess addGuess(String userGuess);
    
    public List<Guess> getAllGuesses();
    
    public void deleteGuessById(int guessId);
}
