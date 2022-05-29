package com.example.animelib.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.animelib.R;
import com.example.animelib.ui.favourites.FavouritesFragment;
import com.example.animelib.ui.home.HomeFragment;
import com.example.animelib.ui.viewed.ViewedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.animelib.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SearchView searchView;
    private ActionBar toolbar;
    private static NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setToolbar();

        //нижняя навигация
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_viewed,
                R.id.navigation_favourites
        ).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    //установка параметров для тулбара
    private void setToolbar() {
        toolbar = Objects.requireNonNull(getSupportActionBar());
        toolbar.setHomeButtonEnabled(true);
        toolbar.setDisplayShowHomeEnabled(true);
        toolbar.setDisplayUseLogoEnabled(true);
        toolbar.setDisplayShowTitleEnabled(false);
        toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                toolbar.setLogo(R.drawable.ic_animelib);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                toolbar.setLogo(R.drawable.ic_animelib_d);
                break;
        }
    }

    public static void updateRV(){
        try {
            int f = Objects.requireNonNull(navController.getCurrentDestination()).getId();
            switch (f){
                case R.id.navigation_home:
                    HomeFragment.updateList();
                    break;
                case R.id.navigation_viewed:
                    ViewedFragment.updateList();
                    break;
                case R.id.navigation_favourites:
                    FavouritesFragment.updateList();
                    break;
            }
        } catch (Exception ignored){ }
    }

    //функция поиска внутри тулбара
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_toolbar, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();

                return false;
            }

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onQueryTextChange(String s) {
                try {
                    int f = Objects.requireNonNull(navController.getCurrentDestination()).getId();
                    switch (f){
                        case R.id.navigation_home:
                            HomeFragment.search(s);
                            break;
                        case R.id.navigation_viewed:
                            ViewedFragment.search(s);
                            break;
                        case R.id.navigation_favourites:
                            FavouritesFragment.search(s);
                            break;
                    }
                } catch (Exception ignored){ }
                return false;
            }
        });
        return true;
    }
}