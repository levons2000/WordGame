package com.example.wordgame.models;

public class Player {
    private String playerName;
    private int currentScore;
    private int numberOfGames;
    private int score;
    private int[] topScores = new int[5];
    private int[] topWordScores = new int[5];

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

    public int[] getTopScores() {
        return topScores;
    }

    public void setTopScores(int topScore) {
        int value = topScore;
        for (int i = 0; i < topScores.length; i++) {
            if (value == topScores[i]) {
                return;
            } else if (value > topScores[i]) {
                int currentItem = topScores[i];
                topScores[i] = value;
                value = currentItem;
            }
        }
    }

    public int[] getTopWordScores() {
        return topWordScores;
    }

    public void setTopWordScores(int topWordScore) {

        int value = topWordScore;

        for (int i = 0; i < topWordScores.length; i++) {
            if (value == topWordScores[i]) {
                return;
            } else if (value > topWordScores[i]) {
                int currentItem = topWordScores[i];
                topWordScores[i] = value;
                value = currentItem;
            }
        }
    }

    public void setTopWordScoresToZero() {
        for (int i = 0; i < topWordScores.length; i++) {
            topWordScores[i] = 0;
        }
    }
}
