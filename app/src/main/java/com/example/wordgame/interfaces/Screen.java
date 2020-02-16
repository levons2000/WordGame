package com.example.wordgame.interfaces;

import com.example.wordgame.models.Player;

public interface Screen {
    void save();
    void exit();
    Player player();
    String title = "";
    String currentPlayer = "";
}
