package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe,container,false);

        TextView titleText = view.findViewById(R.id.text_recipe_title);
        if (getArguments()!=null){
            String recipeId = getArguments().getString("recipeId");
            titleText.setText("Showing data for:"+recipeId);


        }
        return view;
    }
}