package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class AddEditIngredientFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_ingredient, container, false);

        EditText inputName = view.findViewById(R.id.edit_text_name);
        Button btnSave = view.findViewById(R.id.btn_save_ingredient);

        if (getArguments() != null) {
            String existingId = getArguments().getString("ingredientId");
            if (existingId != null) {
                // load the ingredient with this id and fill inputName
            }
        }

        btnSave.setOnClickListener(v ->
                Navigation.findNavController(view).popBackStack());

        return view;
    }
}