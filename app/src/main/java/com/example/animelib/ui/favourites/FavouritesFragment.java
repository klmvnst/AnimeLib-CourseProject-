package com.example.animelib.ui.favourites;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animelib.adapters.favourite.FavouriteRVConfig;
import com.example.animelib.adapters.main.MainScreenRVConfig;
import com.example.animelib.constatnts.Const;
import com.example.animelib.databinding.FragmentFavouritesBinding;
import com.example.animelib.firebase.Anime;
import com.example.animelib.firebase.DataStatus;
import com.example.animelib.firebase.FireBaseHelper;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;
import java.util.Objects;

public class FavouritesFragment extends Fragment {

    private FavouritesViewModel favouritesViewModel;
    private FragmentFavouritesBinding binding;
    private static Query query;
    private static Context thisContext;
    private static RecyclerView rvCards;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        favouritesViewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);

        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();

        return root;
    }

    private void init() {
        thisContext = requireContext();
        rvCards = binding.rvFavourites;
        query = FirebaseDatabase.getInstance().getReference(Const.DOCUMENT_TITLE);
        initCardItem();
    }

    private static void initCardItem() {
        //вывод данных
        new FireBaseHelper(query, thisContext).readFavouriteData((anime, keys) ->
                new FavouriteRVConfig().setConfig(rvCards, thisContext, anime));
    }

    public static void search(String query){
        Query ref = FirebaseDatabase
                .getInstance()
                .getReference(Const.DOCUMENT_TITLE)
                .orderByChild("name")
                .startAt(query)
                .endAt(query + Const.DOT);
        new FireBaseHelper(ref, thisContext).readFavouriteData((anime, keys) ->
                new FavouriteRVConfig().setConfig(rvCards, thisContext, anime));
    }

    public static void updateList(){
        initCardItem();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}