package com.example.animelib.adapters.main;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animelib.activities.BrowseActivity;
import com.example.animelib.constatnts.LogTag;
import com.example.animelib.firebase.Anime;

import java.util.List;
import java.util.Objects;

public class MainScreenCardAdapter extends RecyclerView.Adapter<MainScreenCard> {

    private final List<Anime> cardList;
    private final Context context;

    public MainScreenCardAdapter(List<Anime> cards, Context context) {
        this.cardList = cards;
        this.context = context;
    }

    @NonNull
    @Override
    public MainScreenCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainScreenCard(parent, context);
    }

    @Override
    public void onBindViewHolder(MainScreenCard holder, int position) {
        holder.bind(cardList.get(position));
        holder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), BrowseActivity.class);
            i.putExtra("key", cardList.get(position).getKey());
            i.putExtra("name", cardList.get(position).getName());
            i.putExtra("type", cardList.get(position).getType());
            i.putExtra("episodes", cardList.get(position).getEpisodes());
            i.putExtra("genre", cardList.get(position).getGenre());
            i.putExtra("duration", cardList.get(position).getDuration());
            i.putExtra("description", cardList.get(position).getDescription());
            i.putExtra("date", cardList.get(position).getDate());
            i.putExtra("image", cardList.get(position).getImage());
            i.putExtra("video", cardList.get(position).getVideo());
            Log.i(LogTag.ID_PUT_STRING, Objects.requireNonNull(cardList.get(position).getKey()));
            view.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
