package com.example.wordgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wordgame.adapters.LetterAdapter;
import com.example.wordgame.interfaces.Screen;
import com.example.wordgame.models.LetterModel;
import com.example.wordgame.models.Player;
import com.example.wordgame.models.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayWordGameActivity extends AppCompatActivity implements Screen {

    private Player currentPlayer;
    private Settings settings;
    private RecyclerView letterRecyclerView;
    private List<LetterModel> allLetters = Arrays.asList(
            new LetterModel('A'),
            new LetterModel('E'),
            new LetterModel('I'),
            new LetterModel('O'),
            new LetterModel('U'),
            new LetterModel('B'),
            new LetterModel('C'),
            new LetterModel('D'),
            new LetterModel('F'),
            new LetterModel('G'),
            new LetterModel('H'),
            new LetterModel('J'),
            new LetterModel('K'),
            new LetterModel('L'),
            new LetterModel('M'),
            new LetterModel('N'),
            new LetterModel('P'),
            new LetterModel('Q'),
            new LetterModel('R'),
            new LetterModel('S'),
            new LetterModel('T'),
            new LetterModel('V'),
            new LetterModel('W'),
            new LetterModel('X'),
            new LetterModel('Y'),
            new LetterModel('Z')
    );
    private List<LetterModel> currentList;

    private EditText wordInput;
    private TextView timerText;
    private ImageButton enterButton;
    private ImageButton refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_word_game);
        initCurrentPlayer(getIntent());
        initSettings(getIntent());
        initRecyclerView();
        initViews();
        startTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        save();
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

    private void initRecyclerView() {
        letterRecyclerView = findViewById(R.id.word_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, settings.getSquareBoardSize()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };

        letterRecyclerView.setHasFixedSize(true);
        letterRecyclerView.setLayoutManager(gridLayoutManager);
        currentList = getListWithRandomLetters((settings.getSquareBoardSize() * settings.getSquareBoardSize()));
        LetterAdapter letterAdapter = new LetterAdapter(currentList, this);
        letterRecyclerView.setAdapter(letterAdapter);
    }

    private void initViews() {
        wordInput = findViewById(R.id.word_edit_text);
        enterButton = findViewById(R.id.word_enter_button);
        timerText = findViewById(R.id.timer_text);
        refreshButton = findViewById(R.id.board_refresh_button);
        wordInput.setEnabled(false);
        enterButton.setEnabled(false);
        refreshButton.setEnabled(false);
    }

    private List<LetterModel> getListWithRandomLetters(int count) {
        List<LetterModel> letterModels = new ArrayList<>();
        for (int i = 0; i < (count / 5); ++i) {
            int randomNumber = (new Random()).nextInt(4);
            letterModels.add(allLetters.get(randomNumber));
        }

        for (int i = 0; i < (count - (count / 5)); ++i) {
            int randomNumber = (new Random()).nextInt(20);
            letterModels.add(allLetters.get(randomNumber + 5));
        }

        Collections.shuffle(letterModels);
        Collections.shuffle(letterModels);
        Collections.shuffle(letterModels);
        return letterModels;
    }

    private void startTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int count = settings.getMinutesToEnd() * 60;

            @Override
            public void run() {
                runOnUiThread(() -> {
                    timerText.setText(String.format("Time Remaining: %d", count));
                    if (count == 0) {
                        timerText.setText("Write A Word");
                        wordInput.setEnabled(true);
                        refreshButton.setEnabled(true);
                        enterButton.setEnabled(true);
                        timer.cancel();
                    }
                    --count;
                });
            }
        }, 0, 1000);
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
}
