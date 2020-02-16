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

public class SettingsActivity extends AppCompatActivity implements Screen, SeekBar.OnSeekBarChangeListener {

    private Settings settings;
    private Player currentPlayer;

    private TextView minutesToEndText;
    private TextView squareBoardSizeText;
    private TextView weightOfLettersText;


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
        save();
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("minutesToEnd", settings.getMinutesToEnd());
        resultIntent.putExtra("squareBoardSize", settings.getSquareBoardSize());
        resultIntent.putExtra("weightOfLetters", settings.getWeightOfLetters());
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
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
        SeekBar minutesToEndSeekBar = findViewById(R.id.minutes_to_end_seek_bar);
        minutesToEndText = findViewById(R.id.minutes_to_end_text);
        minutesToEndSeekBar.setMax(4);
        minutesToEndSeekBar.setProgress(settings.getMinutesToEnd() - 1);
        minutesToEndText.setText(String.valueOf(settings.getMinutesToEnd()));
        minutesToEndSeekBar.setOnSeekBarChangeListener(this);
        squareBoardSizeText = findViewById(R.id.square_board_size_text);
        SeekBar squareBoardSizeSeekBar = findViewById(R.id.square_board_size_seek_bar);
        squareBoardSizeSeekBar.setMax(4);
        squareBoardSizeSeekBar.setProgress(settings.getSquareBoardSize() - 4);
        squareBoardSizeText.setText(settings.getSquareBoardSize() + " X " + settings.getSquareBoardSize());
        squareBoardSizeSeekBar.setOnSeekBarChangeListener(this);
        weightOfLettersText = findViewById(R.id.weight_of_letters_text);
        SeekBar weightOfLettersSeekBar = findViewById(R.id.weight_of_letters_seek_bar);
        weightOfLettersSeekBar.setProgress(settings.getWeightOfLetters() - 1);
        weightOfLettersText.setText(String.valueOf(settings.getWeightOfLetters()));
        weightOfLettersSeekBar.setOnSeekBarChangeListener(this);
        weightOfLettersSeekBar.setMax(4);
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.minutes_to_end_seek_bar:
                minutesToEndText.setText(String.valueOf(progress + 1));
                settings.setMinutesToEnd(progress + 1);
                break;
            case R.id.square_board_size_seek_bar:
                squareBoardSizeText.setText((progress + 4) + " X "  + (progress + 4));
                settings.setSquareBoardSize(progress + 4);
                break;
            case R.id.weight_of_letters_seek_bar:
                weightOfLettersText.setText(String.valueOf(progress + 1));
                settings.setWeightOfLetters(progress + 1);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
