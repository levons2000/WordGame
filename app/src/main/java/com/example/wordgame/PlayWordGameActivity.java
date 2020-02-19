package com.example.wordgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
    private LetterAdapter letterAdapter;
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
    private TextView scoreText;
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
        letterAdapter = new LetterAdapter(currentList, this);
        letterRecyclerView.setAdapter(letterAdapter);
    }

    private void initViews() {
        wordInput = findViewById(R.id.word_edit_text);
        enterButton = findViewById(R.id.word_enter_button);
        timerText = findViewById(R.id.timer_text);
        scoreText = findViewById(R.id.score_text);
        refreshButton = findViewById(R.id.board_refresh_button);
        enterButton.setOnClickListener(view -> startMatching(wordInput.getText().toString()));
        refreshButton.setOnClickListener(view -> refreshBoard());
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
        wordInput.setEnabled(true);
        refreshButton.setEnabled(true);
        enterButton.setEnabled(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            int count = settings.getMinutesToEnd() * 60;

            @Override
            public void run() {
                runOnUiThread(() -> {
                    timerText.setText(String.format("Time Remaining: %d", count));
                    if (count == 0) {
                        wordInput.setEnabled(false);
                        refreshButton.setEnabled(false);
                        enterButton.setEnabled(false);
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

    private void startMatching(String word) {
        char[] characters = word.toUpperCase().toCharArray();

        if (characters.length < 2) {
            return;
        }

        for (int i = 0; i < (int) Math.sqrt(currentList.size()); ++i) {
            for (int j = 0; j < (int) Math.sqrt(currentList.size()); ++j) {
                if (currentList.get(i * settings.getSquareBoardSize() + j).getLetter() == characters[0]) {
                    if (doCharacterMatching(characters, i, j)) {
                        currentPlayer.setCurrentScore(currentPlayer.getCurrentScore() + 1);
                        scoreText.setText(String.valueOf(currentPlayer.getCurrentScore()));
                        return;
                    }
                }
            }
        }
    }

    private boolean doCharacterMatching(char[] letters, int line, int column) {
        return doRightCheck(letters, line, column, 1) ||
               doLeftCheck(letters, line, column, 1) ||
               doTopCheck(letters, line, column, 1) ||
               doBottomCheck(letters, line, column, 1) ||
               doTopRightCheck(letters, line, column, 1) ||
               doTopLeftCheck(letters, line, column, 1) ||
               doBottomRightCheck(letters, line, column, 1) ||
               doBottomLeftCheck(letters, line, column, 1);
    }

    private boolean doRightCheck(char[] letters, int line, int column, int index) {
        boolean isRightSide = column == (settings.getSquareBoardSize() - 1);
        boolean finalResult;
        if (!isRightSide) {

            if (index == letters.length) {
                return true;
            }

            boolean result = currentList.get((line * settings.getSquareBoardSize() + column) + 1).getLetter() == letters[index];

            if (result) {
                finalResult = doRightCheck(letters, line, column + 1, ++index);
            } else {
                return false;
            }

        } else {
            return index == letters.length;
        }

        return finalResult;
    }

    private boolean doLeftCheck(char[] letters, int line, int column, int index) {
        boolean isLeftSide = column == 0;
        boolean finalResult;
        if (!isLeftSide) {

            if (index == letters.length) {
                return true;
            }

            boolean result = currentList.get((line * settings.getSquareBoardSize() + column) - 1).getLetter() == letters[index];

            if (result) {
                finalResult = doLeftCheck(letters, line, column - 1, ++index);
            } else {
                return false;
            }

        } else {
            return index == letters.length;
        }

        return finalResult;
    }

    private boolean doTopCheck(char[] letters, int line, int column, int index) {
        boolean isTopSide = line == 0;
        boolean finalResult;
        if (!isTopSide) {
            if (index == letters.length) {
                return true;
            }

            boolean result = currentList.get((line - 1) * settings.getSquareBoardSize() + column).getLetter() == letters[index];

            if (result) {
                finalResult = doTopCheck(letters, line - 1, column, ++index);
            } else {
                return false;
            }

        } else {
            return index == letters.length;
        }

        return finalResult;
    }

    private boolean doBottomCheck(char[] letters, int line, int column, int index) {
        boolean isBottomSide = line == (settings.getSquareBoardSize() - 1);
        boolean finalResult;
        if (!isBottomSide) {
            if (index == letters.length) {
                return true;
            }

            boolean result = currentList.get((line + 1) * settings.getSquareBoardSize() + column).getLetter() == letters[index];

            if (result) {
                finalResult = doBottomCheck(letters, line + 1, column, ++index);
            } else {
                return false;
            }

        } else {
            return index == letters.length;
        }

        return finalResult;
    }

    private boolean doTopRightCheck(char[] letters, int line, int column, int index) {
        boolean isTopSide = line == 0;
        boolean isRightSide = column == (settings.getSquareBoardSize() - 1);
        boolean finalResult;
        if (!isTopSide && !isRightSide) {
            if (index == letters.length) {
                return true;
            }

            boolean result = currentList.get(((line - 1) * settings.getSquareBoardSize() + column) + 1).getLetter() == letters[index];

            if (result) {
                finalResult = doTopRightCheck(letters, line - 1, column + 1, ++index);
            } else {
                return false;
            }

        } else {
            return index == letters.length;
        }

        return finalResult;
    }

    private boolean doTopLeftCheck(char[] letters, int line, int column, int index) {
        boolean isTopSide = line == 0;
        boolean isLeftSide = column == 0;
        boolean finalResult;
        if (!isTopSide && !isLeftSide) {
            if (index == letters.length) {
                return true;
            }

            boolean result = currentList.get(((line - 1) * settings.getSquareBoardSize() + column) - 1).getLetter() == letters[index];

            if (result) {
                finalResult = doTopLeftCheck(letters, line - 1, column - 1, ++index);
            } else {
                return false;
            }

        } else {
            return index == letters.length;
        }

        return finalResult;
    }

    private boolean doBottomRightCheck(char[] letters, int line, int column, int index) {
        boolean isBottomSide = line == (settings.getSquareBoardSize() - 1);
        boolean isRightSide = column == (settings.getSquareBoardSize() - 1);
        boolean finalResult;
        if (!isBottomSide && !isRightSide) {
            if (index == letters.length) {
                return true;
            }

            boolean result = currentList.get(((line + 1) * settings.getSquareBoardSize() + column) + 1).getLetter() == letters[index];

            if (result) {
                finalResult = doBottomRightCheck(letters, line + 1, column + 1, ++index);
            } else {
                return false;
            }

        } else {
            return index == letters.length;
        }

        return finalResult;
    }

    private boolean doBottomLeftCheck(char[] letters, int line, int column, int index) {
        boolean isBottomSide = line == (settings.getSquareBoardSize() - 1);
        boolean isLeftSide = column == 0;
        boolean finalResult;
        if (!isBottomSide && !isLeftSide) {
            if (index == letters.length) {
                return true;
            }

            boolean result = currentList.get(((line + 1) * settings.getSquareBoardSize() + column) - 1).getLetter() == letters[index];

            if (result) {
                finalResult = doBottomLeftCheck(letters, line + 1, column - 1 , ++index);
            } else {
                return false;
            }

        } else {
            return index == letters.length;
        }

        return finalResult;
    }

    private void refreshBoard() {
        currentList = getListWithRandomLetters((settings.getSquareBoardSize() * settings.getSquareBoardSize()));
        letterAdapter.setList(currentList);
        letterAdapter.notifyDataSetChanged();
        currentPlayer.setCurrentScore(currentPlayer.getCurrentScore() - 5);
        scoreText.setText(String.valueOf(currentPlayer.getCurrentScore()));
    }
}
