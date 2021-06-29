/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullsandcows.service;

import com.sg.bullsandcows.models.Game;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jonat
 */
@Repository
public class BACServiceImpl implements BACService {

    @Override
    public String createAnswer() {

        // create a list of all numbers from 0 to 9
        var listOfNumbers = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            listOfNumbers.add(i);
        }
        // shuffle the order of the numbers
        Collections.shuffle(listOfNumbers);
        //  creates a sublist of 4 digits to use as the code
        List<Integer> fourDigits = listOfNumbers.subList(0, 4);
        // convert to a String and return
        String answer = fourDigits.get(0).toString();
        for (int i = 1; i < 4; i++) {
            answer = answer + fourDigits.get(i).toString();
        }
        return answer;

    }

    @Override
    public List<Game> hideListAnswers(List<Game> allGames) {
        for (Game game : allGames) {
            if (game.getInProgress() == true) {
                game.setAnswer("<<hidden>>");
            }
        }
        return allGames;
    }

    @Override
    public Game hideAnswer(Game game) {
        if (game.getInProgress() == true) {
            game.setAnswer("<<hidden>>");
            return game;
        } else {
            return game;
        }
    }

    @Override
    public Boolean validateGuess(String userNumber) {
        return Pattern.matches("^(?:([0-9])(?!.*\\1)){4}$", userNumber);
    }

    @Override
    public Map<String, Integer> scoreGuess(String answer, String userNumber) {
        int e = 0;
        int p = 0;
        Map<String, Integer> rawResults = new HashMap<>();
        for (int i = 0; i < userNumber.length(); i++) {
            if (!answer.contains(String.valueOf(userNumber.charAt(i)))) {
                e = e;
                p = p;
            } else if (answer.charAt(i) == userNumber.charAt(i)) {
                e++;
            } else {
                p++;
            }
        }
        rawResults.put("e", e);
        rawResults.put("p", p);
        return rawResults;
    }

    @Override
    public Game completeGame(Game game, Map<String, Integer> score) {
        if (score.get("e") == 4) {
            game.setInProgress(false);
        }
        return game;
    }
    
    

}
