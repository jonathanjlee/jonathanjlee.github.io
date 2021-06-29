/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author jonat
 */
public class Guess {

    private int guessId;
    private String guess;
    private LocalDateTime time;

    public Guess() {
    }
    
    public Guess(String guess, LocalDateTime time) {
        this.guess = guess;
        this.time = time;
    }

    public int getGuessId() {
        return guessId;
    }

    public void setGuessId(int guessId) {
        this.guessId = guessId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.guessId;
        hash = 37 * hash + Objects.hashCode(this.guess);
        hash = 37 * hash + Objects.hashCode(this.time);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Guess other = (Guess) obj;
        if (this.guessId != other.guessId) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Guess{" + "guessId=" + guessId + ", guess=" + guess + ", time=" + time + '}';
    }

}
