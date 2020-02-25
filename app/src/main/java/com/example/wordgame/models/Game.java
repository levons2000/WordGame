package com.example.wordgame.models;

public class Game {
    private int score;
    private int resetCount;
    private int wordEntered;
    private int boardSize;
    private int numberOfMinutes;
    private int bestWordScore;

    public Game(int score, int resetCount, int wordEntered,
                int boardSize, int numberOfMinutes, int bestWordScore) {
        this.score = score;
        this.resetCount = resetCount;
        this.wordEntered = wordEntered;
        this.boardSize = boardSize;
        this.numberOfMinutes = numberOfMinutes;
        this.bestWordScore = bestWordScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getResetCount() {
        return resetCount;
    }

    public void setResetCount(int resetCount) {
        this.resetCount = resetCount;
    }

    public int getWordEntered() {
        return wordEntered;
    }

    public void setWordEntered(int wordEntered) {
        this.wordEntered = wordEntered;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getNumberOfMinutes() {
        return numberOfMinutes;
    }

    public void setNumberOfMinutes(int numberOfMinutes) {
        this.numberOfMinutes = numberOfMinutes;
    }

    public int getBestWordScore() {
        return bestWordScore;
    }

    public void setBestWordScore(int bestWordScore) {
        this.bestWordScore = bestWordScore;
    }
}
