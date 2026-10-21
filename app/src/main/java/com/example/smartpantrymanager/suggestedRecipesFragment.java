package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link suggestedRecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class suggestedRecipesFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggested_recipes, container, false);

        String selectedRecipeId = "recipe_uqhamo_123";
        Bundle bundle = new Bundle();
        bundle.putString("recipeid", selectedRecipeId);

        View itemRow = view.findViewById(R.id.btn_get_recipes);//recipe list item mock
        itemRow.setOnClickListener(v -> {
            itemRow.setOnClickListener(v -> {
                Navigation.findNavController(view).navigate(R.id.action_suggested_to_detail, bundle);
            });
            return view;
        });

}