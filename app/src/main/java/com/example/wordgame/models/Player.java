package com.example.wordgame.models;

public class Player {
    private String playerName;
    private int currentScore;
    private int numberOfGames;
    private int score;

    public Player(final String playerName) {
        this.playerName = playerName;
        currentScore = 0;
        numberOfGames = 0;
        score = 0;
    }

    public Player(final String playerName, int currentScore, int numberOfGames, int score) {
        this.playerName = playerName;
        this.currentScore = currentScore;
        this.numberOfGames = numberOfGames;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
