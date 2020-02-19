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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        currentPlayer.setTopScores(Integer.valueOf(scoreText.getText().toString()));
        resultIntent.putExtra("firstScore", currentPlayer.getTopScores()[0]);
        resultIntent.putExtra("secondScore", currentPlayer.getTopScores()[1]);
        resultIntent.putExtra("thirdScore", currentPlayer.getTopScores()[2]);
        resultIntent.putExtra("fourthScore", currentPlayer.getTopScores()[3]);
        resultIntent.putExtra("fifthScore", currentPlayer.getTopScores()[4]);
        resultIntent.putExtra("firstWord", currentPlayer.getTopWordScores()[0]);
        resultIntent.putExtra("secondWord", currentPlayer.getTopWordScores()[1]);
        resultIntent.putExtra("thirdWord", currentPlayer.getTopWordScores()[2]);
        resultIntent.putExtra("fourthWord", currentPlayer.getTopWordScores()[3]);
        resultIntent.putExtra("fifthWord", currentPlayer.getTopWordScores()[4]);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SP", 0);
        pref.edit().putInt("firstScore", currentPlayer.getTopScores()[0]).apply();
        pref.edit().putInt("secondScore", currentPlayer.getTopScores()[1]).apply();
        pref.edit().putInt("thirdScore", currentPlayer.getTopScores()[2]).apply();
        pref.edit().putInt("fourthScore", currentPlayer.getTopScores()[3]).apply();
        pref.edit().putInt("fifthScore", currentPlayer.getTopScores()[4]).apply();
        pref.edit().putInt("firstWord", currentPlayer.getTopWordScores()[0]).apply();
        pref.edit().putInt("secondWord", currentPlayer.getTopWordScores()[1]).apply();
        pref.edit().putInt("thirdWord", currentPlayer.getTopWordScores()[2]).apply();
        pref.edit().putInt("fourthWord", currentPlayer.getTopWordScores()[3]).apply();
        pref.edit().putInt("fifthWord", currentPlayer.getTopWordScores()[4]).apply();
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
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
            Map<String, Integer> weightMap = new HashMap<>();
            initWeightMap(weightMap, intent);
            settings = new Settings(minutesToEnd, squareBoardSize, weightMap);
        } else {
            settings = new Settings();
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

    private void startMatching(String word) {
        char[] characters = word.toUpperCase().toCharArray();

        if (characters.length < 2) {
            return;
        }

        if (word.toUpperCase().contains("Q")) {
            for (int i = 0; i < characters.length; i++) {
                if (characters[i] == 'Q') {
                    if(i != characters.length - 1) {
                        if (characters[i + 1] != 'U') {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        for (int i = 0; i < (int) Math.sqrt(currentList.size()); ++i) {
            for (int j = 0; j < (int) Math.sqrt(currentList.size()); ++j) {
                if (currentList.get(i * settings.getSquareBoardSize() + j).getLetter() == characters[0]) {
                    if (doCharacterMatching(characters, i, j)) {
                        int point = 0;
                        for (char character : characters) {
                            point = point + settings.getWeightsOfLetters().get(String.valueOf(character));
                        }
                        currentPlayer.setCurrentScore(currentPlayer.getCurrentScore() + point);
                        scoreText.setText(String.valueOf(currentPlayer.getCurrentScore()));
                        currentPlayer.setTopWordScores(point);
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

            boolean result = true;

            if (letters[index] == 'U') {
                if (letters[index - 1] == 'Q') {
                    result = true;
                } else {
                    result = currentList.get((line * settings.getSquareBoardSize() + column) + 1).getLetter() == letters[index];
                }
            } else if (letters[index - 1] == 'Q') {
                result = false;
            }

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

            boolean result = true;

            if (letters[index] == 'U') {
                if (letters[index - 1] == 'Q') {
                    result = true;
                } else {
                    result = currentList.get((line * settings.getSquareBoardSize() + column) - 1).getLetter() == letters[index];
                }
            } else if (letters[index - 1] == 'Q') {
                result = false;
            }

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

            boolean result = true;

            if (letters[index] == 'U') {
                if (letters[index - 1] == 'Q') {
                    result = true;
                } else {
                    result = currentList.get((line - 1) * settings.getSquareBoardSize() + column).getLetter() == letters[index];
                }
            } else if (letters[index - 1] == 'Q') {
                result = false;
            }

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

            boolean result = true;

            if (letters[index] == 'U') {
                if (letters[index - 1] == 'Q') {
                    result = true;
                } else {
                    result = currentList.get((line + 1) * settings.getSquareBoardSize() + column).getLetter() == letters[index];
                }
            } else if (letters[index - 1] == 'Q') {
                result = false;
            }

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

            boolean result = true;

            if (letters[index] == 'U') {
                if (letters[index - 1] == 'Q') {
                    result = true;
                } else {
                    result = currentList.get(((line - 1) * settings.getSquareBoardSize() + column) + 1).getLetter() == letters[index];
                }
            } else if (letters[index - 1] == 'Q') {
                result = false;
            }

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

            boolean result = true;

            if (letters[index] == 'U') {
                if (letters[index - 1] == 'Q') {
                    result = true;
                } else {
                    result = currentList.get(((line - 1) * settings.getSquareBoardSize() + column) - 1).getLetter() == letters[index];
                }
            } else if (letters[index - 1] == 'Q') {
                result = false;
            }

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

            boolean result = true;

            if (letters[index] == 'U') {
                if (letters[index - 1] == 'Q') {
                    result = true;
                } else {
                    result = currentList.get(((line + 1) * settings.getSquareBoardSize() + column) + 1).getLetter() == letters[index];
                }
            } else if (letters[index - 1] == 'Q') {
                result = false;
            }

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

            boolean result = true;

            if (letters[index] == 'U') {
                if (letters[index - 1] == 'Q') {
                    result = true;
                } else {
                    result = currentList.get(((line + 1) * settings.getSquareBoardSize() + column) - 1).getLetter() == letters[index];
                }
            } else if (letters[index - 1] == 'Q') {
                result = false;
            }

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
