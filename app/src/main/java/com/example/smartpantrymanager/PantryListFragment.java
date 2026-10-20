package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PantryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PantryListFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantry_list,container,false);

        Button btnAdd = view.findViewById(R.id.btn_add_ingredient);
        Button btnGetRecipes = view.findViewById(R.id.btn_get_recipes);

        btnAdd.setOnClickListener(v -> {

            Navigation.findNavController(view).navigate(R.id.action_pantry_to_addEdit);
        });
        btnGetRecipes.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_pantry_to_suggested);
        });
        // Inflate the layout for this fragment
        return view;
    }
}