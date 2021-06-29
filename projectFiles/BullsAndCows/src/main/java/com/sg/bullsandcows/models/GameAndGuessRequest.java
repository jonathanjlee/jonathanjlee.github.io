/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.models;

import java.util.Objects;

/**
 *
 * @author jonat
 */
public class GameAndGuessRequest {

    private int gameId;
    private String userNumber;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.gameId;
        hash = 67 * hash + Objects.hashCode(this.userNumber);
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
        final GameAndGuessRequest other = (GameAndGuessRequest) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.userNumber, other.userNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GameAndGuessRequest{" + "gameId=" + gameId + ", userNumber=" + userNumber + '}';
    }

}
