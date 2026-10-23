package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SuggestedRecipes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        DatabaseHelper db = new DatabaseHelper(this);

        List<Recipe> allRecipes = db.getAllRecipes();

        Toast.makeText(
                this,
                "Recipes loaded: " + allRecipes.size(),
                Toast.LENGTH_LONG
        ).show();

        RecipeFilter.Result result =
                RecipeFilter.filterRecipes(allRecipes, db.getPantryNames());

        showList(
                R.id.recycleViewRecipes,
                R.id.tvNoRecipes,
                result.strictMatches
        );

        showList(
                R.id.recycleViewRecipes,
                R.id.tvNoRecipes,
                allRecipes
        );

        RecyclerView almostThere = findViewById(R.id.rvAlmostThere);
        almostThere.setVisibility(View.GONE);

        findViewById(R.id.tvNoAlmost).setVisibility(View.GONE);
    }

    private void showList(int listId, int emptyId, List<Recipe> recipes) {
        RecyclerView rv = findViewById(listId);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecipeAdapter(recipes));

        boolean none = recipes.isEmpty();

        rv.setVisibility(none ? View.GONE : View.VISIBLE);
        findViewById(emptyId).setVisibility(
                none ? View.VISIBLE : View.GONE
        );
    }
}