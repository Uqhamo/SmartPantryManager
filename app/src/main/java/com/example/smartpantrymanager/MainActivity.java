package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SuggestedAdapter suggestedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);


        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHost.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        NavigationUI.setupWithNavController(bottomNav, navController);


        findViewById(R.id.btnAddItems).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddItemsActivity.class)));


        RecyclerView rv = findViewById(R.id.rvSuggestedHome);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        suggestedAdapter = new SuggestedAdapter(() ->
                startActivity(new Intent(MainActivity.this, SuggestedRecipes.class)));
        rv.setAdapter(suggestedAdapter);


        View banner = findViewById(R.id.cardBanner);
        View suggested = findViewById(R.id.suggestedSection);
        navController.addOnDestinationChangedListener((controller, destination, args) -> {
            int vis = destination.getId() == R.id.pantryListFragment ? View.VISIBLE : View.GONE;
            banner.setVisibility(vis);
            suggested.setVisibility(vis);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<String[]> suggestions = dbHelper.getSuggestedRecipes();
        suggestedAdapter.setItems(suggestions);
        findViewById(R.id.tvNoSuggestions)
                .setVisibility(suggestions.isEmpty() ? View.VISIBLE : View.GONE);
    }
}