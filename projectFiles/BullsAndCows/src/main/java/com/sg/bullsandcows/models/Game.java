/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author jonat
 */
public class Game {

    private int gameId;
    private String answer;
    private Boolean inProgress = true;
    private List<Round> rounds = new ArrayList<>();

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getInProgress() {
        return inProgress;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.gameId;
        hash = 59 * hash + Objects.hashCode(this.answer);
        hash = 59 * hash + Objects.hashCode(this.inProgress);
        hash = 59 * hash + Objects.hashCode(this.rounds);
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
        final Game other = (Game) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.answer, other.answer)) {
            return false;
        }
        if (!Objects.equals(this.inProgress, other.inProgress)) {
            return false;
        }
        if (!Objects.equals(this.rounds, other.rounds)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Game{" + "gameId=" + gameId + ", answer=" + answer + ", inProgress=" + inProgress + ", rounds=" + rounds + '}';
    }

}
