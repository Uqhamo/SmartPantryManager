package com.example.smartpantrymanager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final List<Recipe> recipeList;

    public RecipeAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder, int position) {

        Recipe recipe = recipeList.get(position);

        holder.tvRecipeName.setText(recipe.getName());

        String ingredientsStr = String.join(
                ", ", recipe.getRequiredIngredients());

        holder.tvIngredients.setText(ingredientsStr);

        String imageName = recipe.getImageName();

        if (imageName != null && !imageName.trim().isEmpty()) {
            int imageResource = holder.itemView.getContext()
                    .getResources()
                    .getIdentifier(
                            imageName,
                            "drawable",
                            holder.itemView.getContext().getPackageName()
                    );

            if (imageResource != 0) {
                holder.imageRecipe.setImageResource(imageResource);
            } else {
                holder.imageRecipe.setImageResource(R.drawable.scrambledeggs);
            }
        } else {
            holder.imageRecipe.setImageResource(R.drawable.scrambledeggs);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(
                    v.getContext(),
                    RecipeDetailActivity.class
            );

            i.putExtra("name", recipe.getName());
            i.putExtra("ingredients", ingredientsStr);
            i.putExtra("steps", recipe.getSteps());

            v.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView tvRecipeName;
        TextView tvIngredients;
        ImageView imageRecipe;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecipeName = itemView.findViewById(
                    R.id.textViewRecipeTitle);

            tvIngredients = itemView.findViewById(
                    R.id.textViewIngredients);

            imageRecipe = itemView.findViewById(
                    R.id.imageRecipe);
        }
    }
}