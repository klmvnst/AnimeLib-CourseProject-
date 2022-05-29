package com.example.animelib.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animelib.R;
import com.example.animelib.adapters.main.MainScreenRVConfig;
import com.example.animelib.constatnts.Const;
import com.example.animelib.databinding.FragmentHomeBinding;
import com.example.animelib.firebase.Anime;
import com.example.animelib.firebase.DataStatus;
import com.example.animelib.firebase.FireBaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private static Context thisContext;
    private static RecyclerView rvCards;
    private static ToggleButton bShow;
    private static final int itemsCountLimit = 3; //кол-во отображаемых записей до нажатия на кнопку показа

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        init();

        binding.bShowMore.setOnClickListener(view -> initCardItem(itemsCountLimit));

        return root;
    }

    private void init() {
        bShow = binding.bShowMore;
        binding.bShowMore.setSaveEnabled(false);
        rvCards = binding.rvMainCards;
        thisContext = requireContext();
        initCardItem(itemsCountLimit);
    }

    private static void initCardItem(int limit) {
        //вывод данных
        if(!bShow.isChecked()){
            Query ref = FirebaseDatabase.getInstance().getReference(Const.DOCUMENT_TITLE).orderByChild("popularity");
            new FireBaseHelper(ref, thisContext).readData((anime, keys) ->
                    new MainScreenRVConfig().setConfig(rvCards, thisContext, anime));
        }else{
            Query ref = FirebaseDatabase.getInstance().getReference(Const.DOCUMENT_TITLE).orderByChild("popularity").limitToLast(limit);
            new FireBaseHelper(ref, thisContext).readData((anime, keys) ->
                    new MainScreenRVConfig().setConfig(rvCards, thisContext, anime));
        }
    }

    public static void search(String query){
        bShow.setChecked(false);
        Query ref = FirebaseDatabase
                .getInstance()
                .getReference(Const.DOCUMENT_TITLE)
                .orderByChild("name")
                .startAt(query)
                .endAt(query + Const.DOT);
        new FireBaseHelper(ref, thisContext).readData((anime, keys) ->
                new MainScreenRVConfig().setConfig(rvCards, thisContext, anime));
    }

    public static void updateList(){
        initCardItem(itemsCountLimit);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}