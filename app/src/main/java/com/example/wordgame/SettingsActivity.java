package com.example.wordgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wordgame.interfaces.Screen;
import com.example.wordgame.models.Player;
import com.example.wordgame.models.Settings;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity implements Screen, SeekBar.OnSeekBarChangeListener {

    private Settings settings;
    private Player currentPlayer;

    private TextView minutesToEndText;
    private TextView squareBoardSizeText;

    private Spinner letterSpinner;
    private Spinner weightSpinner;

    private Button setWeightButton;

    private Map<String, Integer> weightMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initSettings(getIntent());
        initCurrentPlayer(getIntent());
        initViews();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.back_button_item) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
        resultIntent.putExtra("A", settings.getWeightsOfLetters().get("A"));
        resultIntent.putExtra("E", settings.getWeightsOfLetters().get("E"));
        resultIntent.putExtra("I", settings.getWeightsOfLetters().get("I"));
        resultIntent.putExtra("O", settings.getWeightsOfLetters().get("O"));
        resultIntent.putExtra("U", settings.getWeightsOfLetters().get("U"));
        resultIntent.putExtra("B", settings.getWeightsOfLetters().get("B"));
        resultIntent.putExtra("C", settings.getWeightsOfLetters().get("C"));
        resultIntent.putExtra("D", settings.getWeightsOfLetters().get("D"));
        resultIntent.putExtra("F", settings.getWeightsOfLetters().get("F"));
        resultIntent.putExtra("G", settings.getWeightsOfLetters().get("G"));
        resultIntent.putExtra("H", settings.getWeightsOfLetters().get("H"));
        resultIntent.putExtra("K", settings.getWeightsOfLetters().get("K"));
        resultIntent.putExtra("L", settings.getWeightsOfLetters().get("L"));
        resultIntent.putExtra("M", settings.getWeightsOfLetters().get("M"));
        resultIntent.putExtra("N", settings.getWeightsOfLetters().get("N"));
        resultIntent.putExtra("P", settings.getWeightsOfLetters().get("P"));
        resultIntent.putExtra("Q", settings.getWeightsOfLetters().get("Q"));
        resultIntent.putExtra("R", settings.getWeightsOfLetters().get("R"));
        resultIntent.putExtra("S", settings.getWeightsOfLetters().get("S"));
        resultIntent.putExtra("T", settings.getWeightsOfLetters().get("T"));
        resultIntent.putExtra("V", settings.getWeightsOfLetters().get("V"));
        resultIntent.putExtra("W", settings.getWeightsOfLetters().get("W"));
        resultIntent.putExtra("X", settings.getWeightsOfLetters().get("X"));
        resultIntent.putExtra("Y", settings.getWeightsOfLetters().get("Y"));
        resultIntent.putExtra("Z", settings.getWeightsOfLetters().get("Z"));
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
        pref.edit().putInt("A", settings.getWeightsOfLetters().get("A")).apply();
        pref.edit().putInt("E", settings.getWeightsOfLetters().get("E")).apply();
        pref.edit().putInt("I", settings.getWeightsOfLetters().get("I")).apply();
        pref.edit().putInt("O", settings.getWeightsOfLetters().get("O")).apply();
        pref.edit().putInt("U", settings.getWeightsOfLetters().get("U")).apply();
        pref.edit().putInt("B", settings.getWeightsOfLetters().get("B")).apply();
        pref.edit().putInt("C", settings.getWeightsOfLetters().get("C")).apply();
        pref.edit().putInt("D", settings.getWeightsOfLetters().get("D")).apply();
        pref.edit().putInt("F", settings.getWeightsOfLetters().get("F")).apply();
        pref.edit().putInt("G", settings.getWeightsOfLetters().get("G")).apply();
        pref.edit().putInt("H", settings.getWeightsOfLetters().get("H")).apply();
        pref.edit().putInt("K", settings.getWeightsOfLetters().get("K")).apply();
        pref.edit().putInt("L", settings.getWeightsOfLetters().get("L")).apply();
        pref.edit().putInt("M", settings.getWeightsOfLetters().get("M")).apply();
        pref.edit().putInt("N", settings.getWeightsOfLetters().get("N")).apply();
        pref.edit().putInt("P", settings.getWeightsOfLetters().get("P")).apply();
        pref.edit().putInt("Q", settings.getWeightsOfLetters().get("Q")).apply();
        pref.edit().putInt("R", settings.getWeightsOfLetters().get("R")).apply();
        pref.edit().putInt("S", settings.getWeightsOfLetters().get("S")).apply();
        pref.edit().putInt("T", settings.getWeightsOfLetters().get("T")).apply();
        pref.edit().putInt("V", settings.getWeightsOfLetters().get("V")).apply();
        pref.edit().putInt("W", settings.getWeightsOfLetters().get("W")).apply();
        pref.edit().putInt("X", settings.getWeightsOfLetters().get("X")).apply();
        pref.edit().putInt("Y", settings.getWeightsOfLetters().get("Y")).apply();
        pref.edit().putInt("Z", settings.getWeightsOfLetters().get("Z")).apply();
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

        letterSpinner = findViewById(R.id.letter_spinner);
        weightSpinner = findViewById(R.id.weight_spinner);

        String[] letterArray = new String[] {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "z"
        };

        ArrayAdapter<String> letterSpinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, letterArray);
        letterSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        letterSpinner.setAdapter(letterSpinnerAdapter);

        String[] weightArray = new String[] {
                "1", "2", "3", "4", "5"
        };

        ArrayAdapter<String> weightSpinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, weightArray);
        weightSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(weightSpinnerAdapter);

        setWeightButton = findViewById(R.id.set_weight_button);
        setWeightButton.setOnClickListener(view -> {
            settings.getWeightsOfLetters().put(letterSpinner.getSelectedItem().toString(), Integer.valueOf(weightSpinner.getSelectedItem().toString()));
            Toast.makeText(this, "Weight of " + letterSpinner.getSelectedItem().toString() +
                    " now is " + Integer.valueOf(weightSpinner.getSelectedItem().toString()), Toast.LENGTH_SHORT).show();
        });

        letterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weightSpinner.setSelection(weightMap.get(letterArray[position]) != null ? weightMap.get(letterArray[position]) - 1 : 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSettings(Intent intent) {
        if (intent.getExtras() != null) {
            int minutesToEnd = getIntent().getExtras().getInt("minutesToEnd");
            int squareBoardSize = getIntent().getExtras().getInt("squareBoardSize");
            weightMap = new HashMap<>();
            initWeightMap(weightMap, intent);
            settings = new Settings(minutesToEnd, squareBoardSize, weightMap);
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

    private void initWeightMap(Map<String, Integer> weightsOfLetters, Intent intent) {
        if (intent.getExtras() != null) {
            weightsOfLetters.put("A", intent.getExtras().getInt("A"));
            weightsOfLetters.put("E", intent.getExtras().getInt("E"));
            weightsOfLetters.put("I", intent.getExtras().getInt("I"));
            weightsOfLetters.put("O", intent.getExtras().getInt("O"));
            weightsOfLetters.put("U", intent.getExtras().getInt("U"));
            weightsOfLetters.put("B", intent.getExtras().getInt("B"));
            weightsOfLetters.put("C", intent.getExtras().getInt("C"));
            weightsOfLetters.put("D", intent.getExtras().getInt("D"));
            weightsOfLetters.put("F", intent.getExtras().getInt("F"));
            weightsOfLetters.put("G", intent.getExtras().getInt("G"));
            weightsOfLetters.put("H", intent.getExtras().getInt("H"));
            weightsOfLetters.put("K", intent.getExtras().getInt("K"));
            weightsOfLetters.put("L", intent.getExtras().getInt("L"));
            weightsOfLetters.put("M", intent.getExtras().getInt("M"));
            weightsOfLetters.put("N", intent.getExtras().getInt("N"));
            weightsOfLetters.put("P", intent.getExtras().getInt("P"));
            weightsOfLetters.put("Q", intent.getExtras().getInt("Q"));
            weightsOfLetters.put("R", intent.getExtras().getInt("R"));
            weightsOfLetters.put("S", intent.getExtras().getInt("S"));
            weightsOfLetters.put("T", intent.getExtras().getInt("T"));
            weightsOfLetters.put("V", intent.getExtras().getInt("V"));
            weightsOfLetters.put("W", intent.getExtras().getInt("W"));
            weightsOfLetters.put("X", intent.getExtras().getInt("X"));
            weightsOfLetters.put("Y", intent.getExtras().getInt("Y"));
            weightsOfLetters.put("Z", intent.getExtras().getInt("Z"));
        } else {
            weightsOfLetters.put("A", 1);
            weightsOfLetters.put("E", 1);
            weightsOfLetters.put("I", 1);
            weightsOfLetters.put("O", 1);
            weightsOfLetters.put("U", 1);
            weightsOfLetters.put("B", 1);
            weightsOfLetters.put("C", 1);
            weightsOfLetters.put("D", 1);
            weightsOfLetters.put("F", 1);
            weightsOfLetters.put("G", 1);
            weightsOfLetters.put("H", 1);
            weightsOfLetters.put("K", 1);
            weightsOfLetters.put("L", 1);
            weightsOfLetters.put("M", 1);
            weightsOfLetters.put("N", 1);
            weightsOfLetters.put("P", 1);
            weightsOfLetters.put("Q", 1);
            weightsOfLetters.put("R", 1);
            weightsOfLetters.put("S", 1);
            weightsOfLetters.put("T", 1);
            weightsOfLetters.put("V", 1);
            weightsOfLetters.put("W", 1);
            weightsOfLetters.put("X", 1);
            weightsOfLetters.put("Y", 1);
            weightsOfLetters.put("Z", 1);
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
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
