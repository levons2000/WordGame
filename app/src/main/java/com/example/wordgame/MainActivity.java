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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Screen {

    public static final int PLAY_REQUEST_CODE = 0;
    public static final int SETTING_REQUEST_CODE = 2;

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
            showMainScreen(pref);
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
            showMainScreen(pref);
        });

        builder.show();
    }

    private void showMainScreen(SharedPreferences pref) {
        playWordGameButton.setVisibility(View.VISIBLE);
        viewStatisticsButton.setVisibility(View.VISIBLE);
        adjustGameSettingsButton.setVisibility(View.VISIBLE);
        playerNameText.setVisibility(View.VISIBLE);
        playerNameText.setText(String.format("Player: %s", currentPlayer.getPlayerName()));
        currentSettings = new Settings();
        Map<String, Integer> weightOfLetters = new HashMap<>();
        initWeightMap(weightOfLetters, pref);
        currentSettings.setWeightsOfLetters(weightOfLetters);
    }

    private void initWeightMap(Map<String, Integer> weightsOfLetters, SharedPreferences pref) {
        weightsOfLetters.put("A", pref.getInt("A", 1));
        weightsOfLetters.put("E", pref.getInt("E", 1));
        weightsOfLetters.put("I", pref.getInt("I", 1));
        weightsOfLetters.put("O", pref.getInt("O", 1));
        weightsOfLetters.put("U", pref.getInt("U", 1));
        weightsOfLetters.put("B", pref.getInt("B", 1));
        weightsOfLetters.put("C", pref.getInt("C", 1));
        weightsOfLetters.put("D", pref.getInt("D", 1));
        weightsOfLetters.put("F", pref.getInt("F", 1));
        weightsOfLetters.put("G", pref.getInt("G", 1));
        weightsOfLetters.put("H", pref.getInt("H", 1));
        weightsOfLetters.put("K", pref.getInt("K", 1));
        weightsOfLetters.put("L", pref.getInt("L", 1));
        weightsOfLetters.put("M", pref.getInt("M", 1));
        weightsOfLetters.put("N", pref.getInt("N", 1));
        weightsOfLetters.put("P", pref.getInt("P", 1));
        weightsOfLetters.put("Q", pref.getInt("Q", 1));
        weightsOfLetters.put("R", pref.getInt("R", 1));
        weightsOfLetters.put("S", pref.getInt("S", 1));
        weightsOfLetters.put("T", pref.getInt("T", 1));
        weightsOfLetters.put("V", pref.getInt("V", 1));
        weightsOfLetters.put("W", pref.getInt("W", 1));
        weightsOfLetters.put("X", pref.getInt("X", 1));
        weightsOfLetters.put("Y", pref.getInt("Y", 1));
        weightsOfLetters.put("Z", pref.getInt("Z", 1));
    }

    private void playWordGame() {
        Intent intent = new Intent(MainActivity.this, PlayWordGameActivity.class);
        intent.putExtra("minutesToEnd", currentSettings.getMinutesToEnd());
        intent.putExtra("squareBoardSize", currentSettings.getSquareBoardSize());
        intent.putExtra("A", currentSettings.getWeightsOfLetters().get("A"));
        intent.putExtra("E", currentSettings.getWeightsOfLetters().get("E"));
        intent.putExtra("I", currentSettings.getWeightsOfLetters().get("I"));
        intent.putExtra("O", currentSettings.getWeightsOfLetters().get("O"));
        intent.putExtra("U", currentSettings.getWeightsOfLetters().get("U"));
        intent.putExtra("B", currentSettings.getWeightsOfLetters().get("B"));
        intent.putExtra("C", currentSettings.getWeightsOfLetters().get("C"));
        intent.putExtra("D", currentSettings.getWeightsOfLetters().get("D"));
        intent.putExtra("F", currentSettings.getWeightsOfLetters().get("F"));
        intent.putExtra("G", currentSettings.getWeightsOfLetters().get("G"));
        intent.putExtra("H", currentSettings.getWeightsOfLetters().get("H"));
        intent.putExtra("K", currentSettings.getWeightsOfLetters().get("K"));
        intent.putExtra("L", currentSettings.getWeightsOfLetters().get("L"));
        intent.putExtra("M", currentSettings.getWeightsOfLetters().get("M"));
        intent.putExtra("N", currentSettings.getWeightsOfLetters().get("N"));
        intent.putExtra("P", currentSettings.getWeightsOfLetters().get("P"));
        intent.putExtra("Q", currentSettings.getWeightsOfLetters().get("Q"));
        intent.putExtra("R", currentSettings.getWeightsOfLetters().get("R"));
        intent.putExtra("S", currentSettings.getWeightsOfLetters().get("S"));
        intent.putExtra("T", currentSettings.getWeightsOfLetters().get("T"));
        intent.putExtra("V", currentSettings.getWeightsOfLetters().get("V"));
        intent.putExtra("W", currentSettings.getWeightsOfLetters().get("W"));
        intent.putExtra("X", currentSettings.getWeightsOfLetters().get("X"));
        intent.putExtra("Y", currentSettings.getWeightsOfLetters().get("Y"));
        intent.putExtra("Z", currentSettings.getWeightsOfLetters().get("Z"));
        intent.putExtra("playerName", currentPlayer.getPlayerName());
        intent.putExtra("currentScore", currentPlayer.getCurrentScore());
        intent.putExtra("numberOfGames", currentPlayer.getNumberOfGames());
        intent.putExtra("score", currentPlayer.getScore());
        startActivityForResult(intent, PLAY_REQUEST_CODE);
    }

    private void viewStatistics() {

    }

    private void adjustGameSettings() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        intent.putExtra("minutesToEnd", currentSettings.getMinutesToEnd());
        intent.putExtra("squareBoardSize", currentSettings.getSquareBoardSize());
        intent.putExtra("A", currentSettings.getWeightsOfLetters().get("A"));
        intent.putExtra("E", currentSettings.getWeightsOfLetters().get("E"));
        intent.putExtra("I", currentSettings.getWeightsOfLetters().get("I"));
        intent.putExtra("O", currentSettings.getWeightsOfLetters().get("O"));
        intent.putExtra("U", currentSettings.getWeightsOfLetters().get("U"));
        intent.putExtra("B", currentSettings.getWeightsOfLetters().get("B"));
        intent.putExtra("C", currentSettings.getWeightsOfLetters().get("C"));
        intent.putExtra("D", currentSettings.getWeightsOfLetters().get("D"));
        intent.putExtra("F", currentSettings.getWeightsOfLetters().get("F"));
        intent.putExtra("G", currentSettings.getWeightsOfLetters().get("G"));
        intent.putExtra("H", currentSettings.getWeightsOfLetters().get("H"));
        intent.putExtra("K", currentSettings.getWeightsOfLetters().get("K"));
        intent.putExtra("L", currentSettings.getWeightsOfLetters().get("L"));
        intent.putExtra("M", currentSettings.getWeightsOfLetters().get("M"));
        intent.putExtra("N", currentSettings.getWeightsOfLetters().get("N"));
        intent.putExtra("P", currentSettings.getWeightsOfLetters().get("P"));
        intent.putExtra("Q", currentSettings.getWeightsOfLetters().get("Q"));
        intent.putExtra("R", currentSettings.getWeightsOfLetters().get("R"));
        intent.putExtra("S", currentSettings.getWeightsOfLetters().get("S"));
        intent.putExtra("T", currentSettings.getWeightsOfLetters().get("T"));
        intent.putExtra("V", currentSettings.getWeightsOfLetters().get("V"));
        intent.putExtra("W", currentSettings.getWeightsOfLetters().get("W"));
        intent.putExtra("X", currentSettings.getWeightsOfLetters().get("X"));
        intent.putExtra("Y", currentSettings.getWeightsOfLetters().get("Y"));
        intent.putExtra("Z", currentSettings.getWeightsOfLetters().get("Z"));
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
        pref.edit().putInt("A", currentSettings.getWeightsOfLetters().get("A")).apply();
        pref.edit().putInt("E", currentSettings.getWeightsOfLetters().get("E")).apply();
        pref.edit().putInt("I", currentSettings.getWeightsOfLetters().get("I")).apply();
        pref.edit().putInt("O", currentSettings.getWeightsOfLetters().get("O")).apply();
        pref.edit().putInt("U", currentSettings.getWeightsOfLetters().get("U")).apply();
        pref.edit().putInt("B", currentSettings.getWeightsOfLetters().get("B")).apply();
        pref.edit().putInt("C", currentSettings.getWeightsOfLetters().get("C")).apply();
        pref.edit().putInt("D", currentSettings.getWeightsOfLetters().get("D")).apply();
        pref.edit().putInt("F", currentSettings.getWeightsOfLetters().get("F")).apply();
        pref.edit().putInt("G", currentSettings.getWeightsOfLetters().get("G")).apply();
        pref.edit().putInt("H", currentSettings.getWeightsOfLetters().get("H")).apply();
        pref.edit().putInt("K", currentSettings.getWeightsOfLetters().get("K")).apply();
        pref.edit().putInt("L", currentSettings.getWeightsOfLetters().get("L")).apply();
        pref.edit().putInt("M", currentSettings.getWeightsOfLetters().get("M")).apply();
        pref.edit().putInt("N", currentSettings.getWeightsOfLetters().get("N")).apply();
        pref.edit().putInt("P", currentSettings.getWeightsOfLetters().get("P")).apply();
        pref.edit().putInt("Q", currentSettings.getWeightsOfLetters().get("Q")).apply();
        pref.edit().putInt("R", currentSettings.getWeightsOfLetters().get("R")).apply();
        pref.edit().putInt("S", currentSettings.getWeightsOfLetters().get("S")).apply();
        pref.edit().putInt("T", currentSettings.getWeightsOfLetters().get("T")).apply();
        pref.edit().putInt("V", currentSettings.getWeightsOfLetters().get("V")).apply();
        pref.edit().putInt("W", currentSettings.getWeightsOfLetters().get("W")).apply();
        pref.edit().putInt("X", currentSettings.getWeightsOfLetters().get("X")).apply();
        pref.edit().putInt("Y", currentSettings.getWeightsOfLetters().get("Y")).apply();
        pref.edit().putInt("Z", currentSettings.getWeightsOfLetters().get("Z")).apply();
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
                currentSettings.getWeightsOfLetters().put("A", data.getExtras().getInt("A"));
                currentSettings.getWeightsOfLetters().put("B", data.getExtras().getInt("B"));
                currentSettings.getWeightsOfLetters().put("C", data.getExtras().getInt("C"));
                currentSettings.getWeightsOfLetters().put("D", data.getExtras().getInt("D"));
                currentSettings.getWeightsOfLetters().put("E", data.getExtras().getInt("E"));
                currentSettings.getWeightsOfLetters().put("F", data.getExtras().getInt("F"));
                currentSettings.getWeightsOfLetters().put("G", data.getExtras().getInt("G"));
                currentSettings.getWeightsOfLetters().put("H", data.getExtras().getInt("H"));
                currentSettings.getWeightsOfLetters().put("I", data.getExtras().getInt("I"));
                currentSettings.getWeightsOfLetters().put("J", data.getExtras().getInt("J"));
                currentSettings.getWeightsOfLetters().put("K", data.getExtras().getInt("K"));
                currentSettings.getWeightsOfLetters().put("L", data.getExtras().getInt("L"));
                currentSettings.getWeightsOfLetters().put("M", data.getExtras().getInt("M"));
                currentSettings.getWeightsOfLetters().put("N", data.getExtras().getInt("N"));
                currentSettings.getWeightsOfLetters().put("O", data.getExtras().getInt("O"));
                currentSettings.getWeightsOfLetters().put("P", data.getExtras().getInt("P"));
                currentSettings.getWeightsOfLetters().put("Q", data.getExtras().getInt("Q"));
                currentSettings.getWeightsOfLetters().put("R", data.getExtras().getInt("R"));
                currentSettings.getWeightsOfLetters().put("S", data.getExtras().getInt("S"));
                currentSettings.getWeightsOfLetters().put("T", data.getExtras().getInt("T"));
                currentSettings.getWeightsOfLetters().put("U", data.getExtras().getInt("U"));
                currentSettings.getWeightsOfLetters().put("V", data.getExtras().getInt("V"));
                currentSettings.getWeightsOfLetters().put("W", data.getExtras().getInt("W"));
                currentSettings.getWeightsOfLetters().put("X", data.getExtras().getInt("X"));
                currentSettings.getWeightsOfLetters().put("Y", data.getExtras().getInt("Y"));
                currentSettings.getWeightsOfLetters().put("Z", data.getExtras().getInt("Z"));
            }
        }
    }
}
