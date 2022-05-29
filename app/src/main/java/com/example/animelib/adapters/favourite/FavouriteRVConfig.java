package com.example.animelib.adapters.favourite;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animelib.adapters.main.MainScreenCardAdapter;
import com.example.animelib.firebase.Anime;

import java.util.List;

public class FavouriteRVConfig {
    public void setConfig(RecyclerView rv, Context context, List<Anime> animeList){
        FavouriteAdapter favouriteAdapter = new FavouriteAdapter(context, animeList);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(favouriteAdapter);
    }
}
