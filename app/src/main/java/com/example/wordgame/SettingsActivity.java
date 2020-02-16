package com.example.wordgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.wordgame.interfaces.Screen;
import com.example.wordgame.models.Player;
import com.example.wordgame.models.Settings;

public class SettingsActivity extends AppCompatActivity implements Screen {

    private Settings settings;
    private Player currentPlayer;

    private SeekBar minutesToEndSeekBar;
    private TextView minutesToEndText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initSettings(getIntent());
        initCurrentPlayer(getIntent());
        initViews();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("minutesToEnd", settings.getMinutesToEnd());
        resultIntent.putExtra("squareBoardSize", settings.getSquareBoardSize());
        resultIntent.putExtra("weightOfLetters", settings.getWightOfLetters());
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void save() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SP", 0);
        pref.edit().putString("playerName", currentPlayer.getPlayerName()).apply();
        pref.edit().putInt("currentScore", currentPlayer.getCurrentScore()).apply();
        pref.edit().putInt("numberOfGames", currentPlayer.getNumberOfGames()).apply();
        pref.edit().putInt("score", currentPlayer.getScore()).apply();
    }

    @Override
    public void exit() {

    }

    @Override
    public Player player() {
        return currentPlayer;
    }

    private void initViews() {
        minutesToEndSeekBar = findViewById(R.id.minutes_to_end_seek_bar);
        minutesToEndText = findViewById(R.id.minutes_to_end_text);
        minutesToEndSeekBar.setMax(4);
        minutesToEndSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                minutesToEndText.setText(String.valueOf(progress + 1));
                settings.setMinutesToEnd(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        minutesToEndSeekBar.setProgress(settings.getMinutesToEnd() - 1);
        minutesToEndText.setText(String.valueOf(settings.getMinutesToEnd()));
    }

    private void initSettings(Intent intent) {
        if (intent.getExtras() != null) {
            int minutesToEnd = getIntent().getExtras().getInt("minutesToEnd");
            int squareBoardSize = getIntent().getExtras().getInt("squareBoardSize");
            int weightOfLetters = getIntent().getExtras().getInt("weightOfLetters");
            settings = new Settings(minutesToEnd, squareBoardSize, weightOfLetters);
        } else {
            settings = new Settings();
        }
    }

    private void initCurrentPlayer(Intent intent) {
        if (intent.getExtras() != null && intent.getExtras().getString("playerName") != null) {
            currentPlayer = new Player(
                    intent.getExtras().getString("playerName"),
                    intent.getExtras().getInt("currentScore"),
                    intent.getExtras().getInt("numberOfGames"),
                    intent.getExtras().getInt("score")
            );
        }
    }
}
