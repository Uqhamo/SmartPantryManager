package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditIngredientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditIngredientFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_add_edit_ingredient);

        EditText inputName = view.findViewById(R.id.edit_text_name);
        Button btnSave = view.findViewById(R.id.btn_save_ingredient);

        if(getArguments() !=null){
            String existingId = getArguments().getString("ingreadientId");
            if(existingId !=null){

            }

        }
        btnSave.setOnClickListener(v->{
            Navigation.findNavController(view).popBackStack();
        });
        return view;
    }
}