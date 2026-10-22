package com.example.smartpantrymanager;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final List<Recipe> recipeList;

    public RecipeAdapter(List<Recipe> recipeList){
        this.recipeList =recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent,false);
        return new RecipeViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position){
        Recipe recipe = recipeList.get(position);
        holder.tvRecipeName.setText(recipe.getName());

        String ingredientsStr = String.join(",",recipe.getRequiredIngredients());
        holder.tvIngredients.setText(ingredientsStr);
    }
    @Override
    public int getItemCount(){
        return recipeList.size();
    }
    static class RecipeViewHolder extends RecyclerView.ViewHolder{
            TextView tvRecipeName;
            TextView tvIngredients;

            public RecipeViewHolder(@NonNull View itemView){
                super(itemView);
                tvRecipeName = itemView.findViewById(R.id.textViewRecipeTitle);
                tvIngredients = itemView.findViewById(R.id.textViewIngredients);

            }
    }
}
