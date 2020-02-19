package com.example.wordgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.wordgame.models.Player;

public class StatisticActivity extends AppCompatActivity {

    private Player currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        initCurrentPlayer(getIntent());
        initScoreBoard();
        initWordBoard();
    }

    private void initCurrentPlayer(Intent intent) {
        if (intent.getExtras() != null) {
            currentPlayer = new Player(
                    intent.getExtras().getString("playerName"),
                    intent.getExtras().getInt("currentScore"),
                    intent.getExtras().getInt("numberOfGames"),
                    intent.getExtras().getInt("score")
            );

            currentPlayer.setTopScores(intent.getExtras().getInt("firstScore"));
            currentPlayer.setTopScores(intent.getExtras().getInt("secondScore"));
            currentPlayer.setTopScores(intent.getExtras().getInt("thirdScore"));
            currentPlayer.setTopScores(intent.getExtras().getInt("fourthScore"));
            currentPlayer.setTopScores(intent.getExtras().getInt("fifthScore"));
            currentPlayer.setTopWordScores(intent.getExtras().getInt("firstWord"));
            currentPlayer.setTopWordScores(intent.getExtras().getInt("secondWord"));
            currentPlayer.setTopWordScores(intent.getExtras().getInt("thirdWord"));
            currentPlayer.setTopWordScores(intent.getExtras().getInt("fourthWord"));
            currentPlayer.setTopWordScores(intent.getExtras().getInt("fifthWord"));
        }
    }

    private void initScoreBoard() {
        TextView first = findViewById(R.id.top_first);
        TextView second = findViewById(R.id.top_second);
        TextView third = findViewById(R.id.top_third);
        TextView fourth = findViewById(R.id.top_fourth);
        TextView fifth = findViewById(R.id.top_fifth);
        first.setText(String.valueOf(currentPlayer.getTopScores()[0]));
        second.setText(String.valueOf(currentPlayer.getTopScores()[1]));
        third.setText(String.valueOf(currentPlayer.getTopScores()[2]));
        fourth.setText(String.valueOf(currentPlayer.getTopScores()[3]));
        fifth.setText(String.valueOf(currentPlayer.getTopScores()[4]));
    }

    private void initWordBoard() {
        TextView first = findViewById(R.id.word_first);
        TextView second = findViewById(R.id.word_second);
        TextView third = findViewById(R.id.word_third);
        TextView fourth = findViewById(R.id.word_fourth);
        TextView fifth = findViewById(R.id.word_fifth);
        first.setText(String.valueOf(currentPlayer.getTopWordScores()[0]));
        second.setText(String.valueOf(currentPlayer.getTopWordScores()[1]));
        third.setText(String.valueOf(currentPlayer.getTopWordScores()[2]));
        fourth.setText(String.valueOf(currentPlayer.getTopWordScores()[3]));
        fifth.setText(String.valueOf(currentPlayer.getTopWordScores()[4]));
    }
}
