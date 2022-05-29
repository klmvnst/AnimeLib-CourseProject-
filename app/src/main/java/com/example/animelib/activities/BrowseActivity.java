package com.example.animelib.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.animelib.R;
import com.example.animelib.classes.DownloadImageTask;
import com.example.animelib.classes.YouTubePlayerConfig;
import com.example.animelib.constatnts.Const;
import com.example.animelib.constatnts.LogTag;
import com.example.animelib.databinding.ActivityBrowseBinding;
import com.example.animelib.dialogs.ImageDialog;
import com.example.animelib.firebase.FireBaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashSet;
import java.util.Set;

public class BrowseActivity extends YouTubeBaseActivity {

    private ActivityBrowseBinding binding;
    private YouTubePlayer.OnInitializedListener oilPlayer;
    private SharedPreferences prefs;
    private Set<String> favSet, viewSet;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrowseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //инициализация плеера ютуб
        binding.bPlay.setOnClickListener(view -> binding.ypvVideo.initialize(YouTubePlayerConfig.API_KEY, oilPlayer));

        //запуск видео в браузере
        binding.bPlayWeb.setOnClickListener(view -> playVideoInWeb());

        //возврат назад
        binding.bBack.setOnClickListener(view -> onBackPressed());

        //обновление данных страницы по скроллу вверх
        binding.srUpdate.setOnRefreshListener(() -> {
            loadData();
            binding.srUpdate.setRefreshing(false);
        });

        //показ диалога с большой картинкой
        binding.ivPicture.setOnClickListener(view -> {
            ImageDialog imageDialog = new ImageDialog(this, getIntent().getStringExtra("image"));
            imageDialog.show();
        });

        //добваление в избранное
        binding.cbFavourite.setOnClickListener(view -> {
            if(binding.cbFavourite.isChecked()){
                favSet.add(getIntent().getStringExtra("key"));
            }else {
                if(favSet != null){
                    favSet.remove(getIntent().getStringExtra("key"));
                }
            }
            prefs.edit().putStringSet("favourite", favSet).apply();
            MainActivity.updateRV();
            Snackbar.make(BrowseActivity.this, view, "Сохранено", Snackbar.LENGTH_LONG).show();
        });

        //добавление в просмотренное
        binding.cbViewed.setOnClickListener(view -> {
            if(binding.cbViewed.isChecked()){
                viewSet.add(getIntent().getStringExtra("key"));
            }else {
                if(viewSet != null){
                    viewSet.remove(getIntent().getStringExtra("key"));
                }
            }
            prefs.edit().putStringSet(Const.VIEWED, viewSet).apply();
            MainActivity.updateRV();
            Snackbar.make(BrowseActivity.this, view, "Сохранено", Snackbar.LENGTH_LONG).show();
        });

        init();
    }

    //инициализация переменных
    private void init() {
        prefs = getSharedPreferences(Const.PREFERENCES_SAVES, Context.MODE_PRIVATE);
        favSet = new HashSet<>();
        viewSet = new HashSet<>();
        favSet = prefs.getStringSet(Const.FAVOURITE, new HashSet<>());
        viewSet = prefs.getStringSet(Const.VIEWED, new HashSet<>());

        loadData();
        playVideo();
    }

    //загрузка данных
    private void loadData() {
        binding.tvNameBrowse.setText(getIntent().getStringExtra("name"));
        binding.tvType.setText(getString(R.string.title_type, getIntent().getStringExtra("type")));
        binding.tvEpisode.setText(getString(R.string.title_episode, getIntent().getStringExtra("episodes")));
        binding.tvGenre.setText(getString(R.string.title_genre, getIntent().getStringExtra("genre")));
        binding.tvDuration.setText(getString(R.string.title_duration, getIntent().getStringExtra("duration")));
        binding.tvDescription.setText(getString(R.string.title_description, getIntent().getStringExtra("description")));
        binding.tvDate.setText(getString(R.string.title_date, getIntent().getStringExtra("date")));
        binding.cbFavourite.setChecked(getIsFavourite());
        binding.cbViewed.setChecked(getIsViewed());
        new DownloadImageTask(binding.ivPicture).execute(getIntent().getStringExtra("image"));
    }

    //проверка ключа на то избранное ли данное аниме
    public boolean getIsFavourite(){
        return prefs.getStringSet(Const.FAVOURITE, new HashSet<>()).contains(getIntent().getStringExtra("key"));
    }

    //проверка ключа на то просмотренное ли данное аниме
    public boolean getIsViewed(){
        return prefs.getStringSet(Const.VIEWED, new HashSet<>()).contains(getIntent().getStringExtra("key"));
    }

    //октрытие видео по ссылке
    private void playVideoInWeb(){
        String urlForWeb = "https://www.youtube.com/watch?v=" + getIntent().getStringExtra("video");
        String urlForApp = "vnd.youtube://" + getIntent().getStringExtra("video");

        try{
            //попытка запуска приложения ютуб
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlForApp)));
        }catch (Exception exception){
            //если приложение не установлено то откроется браузер
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlForWeb)));
        }

    }

    //метод проигрывания видео
    private void playVideo() {
        oilPlayer = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                binding.ypvVideo.setVisibility(View.VISIBLE);
                youTubePlayer.loadVideo(getIntent().getStringExtra("video"));
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Snackbar.make(binding.getRoot(), "Ошибка воспроизведения", Snackbar.LENGTH_LONG).show();
                Log.i(LogTag.ERROR, youTubeInitializationResult.name());
                //youTubeInitializationResult.getErrorDialog(BrowseActivity.this, 1);
            }
        };
    }
}