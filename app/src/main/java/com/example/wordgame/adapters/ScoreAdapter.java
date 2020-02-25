package com.example.wordgame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordgame.R;
import com.example.wordgame.data.DataUtil;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>{
    private Context context;

    public ScoreAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ScoreAdapter.ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.score_statistic_item_style, parent, false);
        return new ScoreAdapter.ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.ScoreViewHolder holder, int position) {
        holder.score.setText(String.valueOf(DataUtil.games.get(position).getScore()));
        holder.reset.setText(String.valueOf(DataUtil.games.get(position).getResetCount()));
        holder.wordEntered.setText(String.valueOf(DataUtil.games.get(position).getWordEntered()));
    }

    @Override
    public int getItemCount() {
        return DataUtil.games.size();
    }

    class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView score;
        TextView reset;
        TextView wordEntered;
        ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            score = itemView.findViewById(R.id.score_item_text);
            reset = itemView.findViewById(R.id.reset_item_text);
            wordEntered = itemView.findViewById(R.id.word_item_text);
        }
    }
}
