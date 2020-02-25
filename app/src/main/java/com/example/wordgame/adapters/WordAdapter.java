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

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private Context context;

    public WordAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WordAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.word_statistic_item_style, parent, false);
        return new WordAdapter.WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordAdapter.WordViewHolder holder, int position) {
        holder.word.setText(String.valueOf(DataUtil.words.get(position).getWord()));
        holder.used.setText(String.valueOf(DataUtil.words.get(position).getUsed()));
    }

    @Override
    public int getItemCount() {
        return DataUtil.words.size();
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        TextView word;
        TextView used;
        WordViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.word_item_text);
            used = itemView.findViewById(R.id.used_item_text);
        }
    }
}
