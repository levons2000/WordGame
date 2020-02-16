package com.example.wordgame.models;

public class Settings {
    private int minutesToEnd;
    private int squareBoardSize;
    private int weightOfLetters;

    public Settings() {
        minutesToEnd = 3;
        squareBoardSize = 4;
        weightOfLetters = 1;
    }

    public Settings(int minutesToEnd, int squareBoardSize, int weightOfLetters) {
        this.minutesToEnd = minutesToEnd;
        this.squareBoardSize = squareBoardSize;
        this.weightOfLetters = weightOfLetters;
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

    public int getWeightOfLetters() {
        return weightOfLetters;
    }

    public void setWeightOfLetters(int weightOfLetters) {
        if (weightOfLetters >= 1 && weightOfLetters <= 5) {
            this.weightOfLetters = weightOfLetters;
        }
    }
}
