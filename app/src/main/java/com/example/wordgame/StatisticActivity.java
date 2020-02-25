package com.example.wordgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wordgame.adapters.ScoreAdapter;
import com.example.wordgame.adapters.WordAdapter;
import com.example.wordgame.models.Player;

public class StatisticActivity extends AppCompatActivity {

    private Player currentPlayer;
    private Button scoreButton;
    private Button wordButton;
    private LinearLayout statisticLayout;
    private TextView statisticTitle;
    private RecyclerView scoreStatisticRecycler;
    private RecyclerView wordStatisticRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        initViews();
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

    private void initViews() {
        scoreButton = findViewById(R.id.score_statistics);
        wordButton = findViewById(R.id.word_statistics);
        statisticLayout = findViewById(R.id.statistic_layout);
        scoreStatisticRecycler = findViewById(R.id.score_statistic_recycler);
        wordStatisticRecycler = findViewById(R.id.word_statistic_recycler);
        statisticTitle = findViewById(R.id.statistic_title);

        scoreButton.setOnClickListener(view -> {
            scoreButton.setVisibility(View.GONE);
            wordButton.setVisibility(View.GONE);
            statisticTitle.setText("Score Statistic");
            statisticLayout.setVisibility(View.VISIBLE);
            scoreStatisticRecycler.setVisibility(View.VISIBLE);
        });

        wordButton.setOnClickListener(view -> {
            scoreButton.setVisibility(View.GONE);
            wordButton.setVisibility(View.GONE);
            statisticTitle.setText("Word Statistic");
            statisticLayout.setVisibility(View.VISIBLE);
            wordStatisticRecycler.setVisibility(View.VISIBLE);
        });

        ScoreAdapter scoreAdapter = new ScoreAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        scoreStatisticRecycler.setAdapter(scoreAdapter);
        scoreStatisticRecycler.setLayoutManager(linearLayoutManager);
        scoreStatisticRecycler.setHasFixedSize(true);

        WordAdapter wordAdapter = new WordAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        wordStatisticRecycler.setAdapter(wordAdapter);
        wordStatisticRecycler.setLayoutManager(layoutManager);
        wordStatisticRecycler.setHasFixedSize(true);
    }

    private void initScoreBoard() {

    }

    private void initWordBoard() {

    }
}
