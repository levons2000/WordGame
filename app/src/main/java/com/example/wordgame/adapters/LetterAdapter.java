package com.example.wordgame.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordgame.R;
import com.example.wordgame.models.LetterModel;

import java.util.List;

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.LetterViewHolder> {

    private List<LetterModel> letters;
    private Context context;

    public LetterAdapter(List<LetterModel> letters, Context context) {
        this.letters = letters;
        this.context = context;
    }

    @NonNull
    @Override
    public LetterAdapter.LetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.letter_item_style, parent, false);
        return new LetterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LetterAdapter.LetterViewHolder holder, int position) {
        if (String.valueOf(letters.get(position).getLetter()).equals("Q")) {
            holder.textView.setText(String.format("%su", String.valueOf(letters.get(position).getLetter())));
        } else {
            holder.textView.setText(String.valueOf(letters.get(position).getLetter()));
        }
    }

    @Override
    public int getItemCount() {
        return letters.size();
    }

    public void setList(List<LetterModel> list) {
        letters = list;
    }

    class LetterViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
         LetterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.letter_text);
        }
    }
}
