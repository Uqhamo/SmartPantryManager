package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SuggestedAdapter extends RecyclerView.Adapter<SuggestedAdapter.VH> {

    private final ArrayList<String[]> items = new ArrayList<>();
    private final Runnable onCardClick;

    public SuggestedAdapter(Runnable onCardClick) {
        this.onCardClick = onCardClick;
    }

    public void setItems(ArrayList<String[]> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_suggested_recipe, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        String[] item = items.get(position);   // [0] = name, [1] = ingredients, [2] = steps
        h.name.setText(item[0]);
        h.ingredients.setText(item[1]);
        h.itemView.setOnClickListener(v -> onCardClick.run());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView name, ingredients;

        VH(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.tvRecipeName);
            ingredients = v.findViewById(R.id.tvRecipeIngredients);
        }
    }
}