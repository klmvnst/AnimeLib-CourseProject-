package com.example.animelib.adapters.main;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animelib.firebase.Anime;

import java.util.List;

public class MainScreenRVConfig {

    public void setConfig(RecyclerView rv, Context context, List<Anime> animeList){
        MainScreenCardAdapter mainScreenCardAdapter = new MainScreenCardAdapter(animeList, context);
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        rv.setLayoutManager(lm);
        rv.setAdapter(mainScreenCardAdapter);
    }
}
