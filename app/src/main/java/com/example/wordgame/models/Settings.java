package com.example.wordgame.models;

import java.util.HashMap;
import java.util.Map;

public class Settings {
    private int minutesToEnd;
    private int squareBoardSize;
    private Map<String, Integer> weightsOfLetters;

    public Settings() {
        minutesToEnd = 3;
        squareBoardSize = 4;
        weightsOfLetters = new HashMap<>();
        weightsOfLetters.put("A", 1);
        weightsOfLetters.put("E", 1);
        weightsOfLetters.put("I", 1);
        weightsOfLetters.put("O", 1);
        weightsOfLetters.put("U", 1);
        weightsOfLetters.put("B", 1);
        weightsOfLetters.put("C", 1);
        weightsOfLetters.put("D", 1);
        weightsOfLetters.put("F", 1);
        weightsOfLetters.put("G", 1);
        weightsOfLetters.put("H", 1);
        weightsOfLetters.put("K", 1);
        weightsOfLetters.put("L", 1);
        weightsOfLetters.put("M", 1);
        weightsOfLetters.put("N", 1);
        weightsOfLetters.put("P", 1);
        weightsOfLetters.put("Q", 1);
        weightsOfLetters.put("R", 1);
        weightsOfLetters.put("S", 1);
        weightsOfLetters.put("T", 1);
        weightsOfLetters.put("V", 1);
        weightsOfLetters.put("W", 1);
        weightsOfLetters.put("X", 1);
        weightsOfLetters.put("Y", 1);
        weightsOfLetters.put("Z", 1);
    }

    public Settings(int minutesToEnd, int squareBoardSize, Map<String, Integer> weightsOfLetters) {
        this.minutesToEnd = minutesToEnd;
        this.squareBoardSize = squareBoardSize;
        this.weightsOfLetters = weightsOfLetters;
    }

    public int getMinutesToEnd() {
        return minutesToEnd;
    }

    public void setMinutesToEnd(int minutesToEnd) {
        if (minutesToEnd >= 1 && minutesToEnd <= 5) {
            this.minutesToEnd = minutesToEnd;
        }
    }

    public int getSquareBoardSize() {
        return squareBoardSize;
    }

    public void setSquareBoardSize(int squareBoardSize) {
        if (squareBoardSize >= 4 && squareBoardSize <= 8) {
            this.squareBoardSize = squareBoardSize;
        }
    }

    public Map<String, Integer> getWeightsOfLetters() {
        return weightsOfLetters;
    }

    public void setWeightsOfLetters(Map<String, Integer> weightsOfLetters) {
        this.weightsOfLetters = weightsOfLetters;
    }
}
