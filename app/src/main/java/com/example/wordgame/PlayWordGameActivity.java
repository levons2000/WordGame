package com.example.wordgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.wordgame.adapters.LetterAdapter;
import com.example.wordgame.models.LetterModel;

import java.util.Arrays;
import java.util.List;

public class PlayWordGameActivity extends AppCompatActivity {

    private RecyclerView letterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_word_game);
        initRecyclerView();
    }

    private void initRecyclerView() {
        List<LetterModel> letterModelList = Arrays.asList(
                new LetterModel('A'),
                new LetterModel('B'),
                new LetterModel('C'),
                new LetterModel('D'),
                new LetterModel('E'),
                new LetterModel('F'),
                new LetterModel('G'),
                new LetterModel('H'),
                new LetterModel('I'),
                new LetterModel('J'),
                new LetterModel('K'),
                new LetterModel('L'),
                new LetterModel('M'),
                new LetterModel('N'),
                new LetterModel('O'),
                new LetterModel('P')
        );

        letterRecyclerView = findViewById(R.id.word_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        letterRecyclerView.setHasFixedSize(true);
        letterRecyclerView.setLayoutManager(gridLayoutManager);
        LetterAdapter letterAdapter = new LetterAdapter(letterModelList, this);
        letterRecyclerView.setAdapter(letterAdapter);
    }
}
