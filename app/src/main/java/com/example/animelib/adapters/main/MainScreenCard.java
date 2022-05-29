package com.example.animelib.adapters.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.animelib.R;
import com.example.animelib.classes.DownloadImageTask;
import com.example.animelib.constatnts.Const;
import com.example.animelib.firebase.Anime;

import java.util.HashSet;
import java.util.Objects;

public class MainScreenCard extends RecyclerView.ViewHolder {

    private final TextView name;
    private final ImageView image, ivPopular, ivFav, ivView;
    private SharedPreferences prefs;

    public MainScreenCard(ViewGroup parent, Context context){
        super(LayoutInflater.from(context).inflate(R.layout.card_item_main, parent, false));
        name = itemView.findViewById(R.id.tvName);
        image = itemView.findViewById(R.id.ivAnimePicture);
        ivPopular = itemView.findViewById(R.id.ivIsPopular);
        ivFav = itemView.findViewById(R.id.ivIsFavourite);
        ivView = itemView.findViewById(R.id.ivIsViewed);
        prefs = context.getSharedPreferences(Const.PREFERENCES_SAVES, Context.MODE_PRIVATE);
    }

    public void bind(Anime anime){
        name.setText(anime.getName());
        new DownloadImageTask(image).execute(anime.getImage());

        if(anime.getPopularity()) ivPopular.setVisibility(View.VISIBLE);
        else ivPopular.setVisibility(View.GONE);

        if(prefs.getStringSet(Const.VIEWED, new HashSet<>()).contains(anime.getKey())) ivView.setVisibility(View.VISIBLE);
        else ivView.setVisibility(View.GONE);

        if(prefs.getStringSet(Const.FAVOURITE, new HashSet<>()).contains(anime.getKey())) ivFav.setVisibility(View.VISIBLE);
        else ivFav.setVisibility(View.GONE);
    }
}
