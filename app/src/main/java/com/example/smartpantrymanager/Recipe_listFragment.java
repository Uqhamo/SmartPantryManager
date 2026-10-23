package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Recipe_listFragment extends Fragment {

    private final ArrayList<Recipe> recipes = new ArrayList<>();
    private RecipeAdapter adapter;
    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DatabaseHelper(requireContext());
        RecyclerView rv = view.findViewById(R.id.rvAllRecipes);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new RecipeAdapter(recipes);
        rv.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        recipes.clear();
        recipes.addAll(db.getAllRecipes());
        adapter.notifyDataSetChanged();

        View v = getView();
        if (v != null) {
            v.findViewById(R.id.tvNoAllRecipes)
                    .setVisibility(recipes.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }
}