package com.example.animelib.adapters.favourite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.animelib.R;
import com.example.animelib.firebase.Anime;

public class Favourite extends RecyclerView.ViewHolder {

    private final TextView tvName;
    private final ImageView ivPopular;

    public Favourite(ViewGroup parent, Context context){
        super(LayoutInflater.from(context).inflate(R.layout.card_item_fav_viewed, parent, false));
        tvName = itemView.findViewById(R.id.tvAnimeName);
        ivPopular = itemView.findViewById(R.id.ivIsPopularInFavView);
    }

    public void bind(Anime anime){
        tvName.setText(anime.getName());

        if(anime.getPopularity()) ivPopular.setVisibility(View.VISIBLE);
        else ivPopular.setVisibility(View.GONE);
    }
}
