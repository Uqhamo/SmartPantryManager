package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SuggestedRecipes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_suggested_recipes);


        RecyclerView rv = findViewById(R.id.recycleViewRecipes);
        TextView empty = findViewById(R.id.tvNoRecipes);

        ArrayList<Recipe> recipes = new ArrayList<>();
        for (String[] row : new DatabaseHelper(this).getSuggestedRecipes()) {
            ArrayList<String> ingredients = new ArrayList<>();
            for (String s : row[1].split(",")) ingredients.add(s.trim());
            recipes.add(new Recipe(row[0], ingredients, row[2]));
        }

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecipeAdapter(recipes));
        boolean none = recipes.isEmpty();
        rv.setVisibility(none ? View.GONE : View.VISIBLE);
        empty.setVisibility(none ? View.VISIBLE : View.GONE);
    }
}