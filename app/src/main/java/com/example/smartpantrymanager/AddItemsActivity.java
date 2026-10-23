package com.example.smartpantrymanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class AddItemsActivity extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        db = new DatabaseHelper(this);

        View formIngredient = findViewById(R.id.formIngredient);
        View formRecipe = findViewById(R.id.formRecipe);
        TabLayout tabs = findViewById(R.id.tabs);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                boolean ingredient = tab.getPosition() == 0;
                formIngredient.setVisibility(ingredient ? View.VISIBLE : View.GONE);
                formRecipe.setVisibility(ingredient ? View.GONE : View.VISIBLE);
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) { }
            @Override public void onTabReselected(TabLayout.Tab tab) { }
        });

        EditText etName = findViewById(R.id.etName);
        EditText etQty = findViewById(R.id.etQty);
        EditText etUnit = findViewById(R.id.etUnit);
        EditText etExpiry = findViewById(R.id.etExpiry);

        etExpiry.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (p, y, m, d) ->
                    etExpiry.setText(String.format("%04d-%02d-%02d", y, m + 1, d)),
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)).show();
        });

        findViewById(R.id.btnSaveIngredient).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String qtyText = etQty.getText().toString().trim();
            if (name.isEmpty() || qtyText.isEmpty()) {
                Toast.makeText(this, "Name and quantity are required", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean ok = db.insertData(name, Double.parseDouble(qtyText),
                    etUnit.getText().toString().trim(),
                    etExpiry.getText().toString().trim());
            Toast.makeText(this, ok ? "Ingredient saved" : "Could not save", Toast.LENGTH_SHORT).show();
            if (ok) finish();
        });

        EditText etRecipeName = findViewById(R.id.etRecipeName);
        EditText etRecipeIngredients = findViewById(R.id.etRecipeIngredients);
        EditText etRecipeSteps = findViewById(R.id.etRecipeSteps);

        findViewById(R.id.btnSaveRecipe).setOnClickListener(v -> {
            String name = etRecipeName.getText().toString().trim();
            String ingredients = etRecipeIngredients.getText().toString().trim();
            if (name.isEmpty() || ingredients.isEmpty()) {
                Toast.makeText(this, "Recipe name and ingredients are required", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean ok = db.insertRecipe(name, ingredients,
                    etRecipeSteps.getText().toString().trim());
            Toast.makeText(this, ok ? "Recipe saved" : "Could not save", Toast.LENGTH_SHORT).show();
            if (ok) finish();
        });
    }
}
