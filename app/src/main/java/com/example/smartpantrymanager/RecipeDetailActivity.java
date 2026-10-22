package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ((TextView) findViewById(R.id.tvDetailName))
                .setText(getIntent().getStringExtra("name"));
        ((TextView) findViewById(R.id.tvDetailIngredients))
                .setText(getIntent().getStringExtra("ingredients"));
        ((TextView) findViewById(R.id.tvDetailSteps))
                .setText(getIntent().getStringExtra("steps"));
    }
}