package com.example.wordgame;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wordgame.interfaces.Screen;
import com.example.wordgame.models.Player;
import com.example.wordgame.models.Settings;

public class MainActivity extends AppCompatActivity implements Screen {

    public static final int SETTING_REQUEST_CODE = 0;

    private Player currentPlayer;
    private Settings currentSettings;

    private Button playWordGameButton;
    private Button viewStatisticsButton;
    private Button adjustGameSettingsButton;
    private TextView playerNameText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SP", 0);
        String savedPlayerName = pref.getString("playerName", null);
        int savedCurrentScore = pref.getInt("currentScore", 0);
        int savedNumberOfGames = pref.getInt("numberOfGames", 0);
        int savedScore = pref.getInt("score", 0);
        initViews();
        if (savedPlayerName == null) {
            registerNewPlayer();
        } else {
            currentPlayer = new Player(savedPlayerName, savedCurrentScore, savedNumberOfGames, savedScore);
            showMainScreen();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        save();
        exit();
    }

    private void initViews() {
        playWordGameButton = findViewById(R.id.play_word_game);
        playWordGameButton.setOnClickListener(view -> playWordGame());
        viewStatisticsButton = findViewById(R.id.view_statistics);
        viewStatisticsButton.setOnClickListener(view -> viewStatistics());
        adjustGameSettingsButton = findViewById(R.id.adjust_game_settings);
        adjustGameSettingsButton.setOnClickListener(view -> adjustGameSettings());
        playerNameText = findViewById(R.id.player_name);
    }

    private void registerNewPlayer() {
        playWordGameButton.setVisibility(View.INVISIBLE);
        viewStatisticsButton.setVisibility(View.INVISIBLE);
        adjustGameSettingsButton.setVisibility(View.INVISIBLE);
        playerNameText.setVisibility(View.INVISIBLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter the player name");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("SP", 0);
            pref.edit().putString("playerName", input.getText().toString()).apply();
            pref.edit().putInt("currentScore", 0).apply();
            pref.edit().putInt("numberOfGames", 0).apply();
            pref.edit().putInt("score", 0).apply();
            currentPlayer = new Player(input.getText().toString());
            showMainScreen();
        });

        builder.show();
    }

    private void showMainScreen() {
        playWordGameButton.setVisibility(View.VISIBLE);
        viewStatisticsButton.setVisibility(View.VISIBLE);
        adjustGameSettingsButton.setVisibility(View.VISIBLE);
        playerNameText.setVisibility(View.VISIBLE);
        playerNameText.setText(String.format("Player: %s", currentPlayer.getPlayerName()));
        currentSettings = new Settings();
    }

    private void playWordGame() {

    }

    private void viewStatistics() {

    }

    private void adjustGameSettings() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        intent.putExtra("minutesToEnd", currentSettings.getMinutesToEnd());
        intent.putExtra("squareBoardSize", currentSettings.getSquareBoardSize());
        intent.putExtra("weightOfLetters", currentSettings.getWeightOfLetters());
        intent.putExtra("playerName", currentPlayer.getPlayerName());
        intent.putExtra("currentScore", currentPlayer.getCurrentScore());
        intent.putExtra("numberOfGames", currentPlayer.getNumberOfGames());
        intent.putExtra("score", currentPlayer.getScore());
        startActivityForResult(intent, SETTING_REQUEST_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTING_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                currentSettings.setMinutesToEnd(data.getExtras().getInt("minutesToEnd"));
                currentSettings.setSquareBoardSize(data.getExtras().getInt("squareBoardSize"));
                currentSettings.setWeightOfLetters(data.getExtras().getInt("weightOfLetters"));
            }
        }
    }
}
