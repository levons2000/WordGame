package com.example.wordgame.models;

public class Word {
    private String word;
    private int used;

    public Word(String word, int used) {
        this.word = word;
        this.used = used;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
