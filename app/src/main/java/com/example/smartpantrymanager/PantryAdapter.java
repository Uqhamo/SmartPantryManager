package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private final ArrayList<PantryItem> pantryList;
    private final OnItemClickListener editListener;
    private final OnItemClickListener deleteListener;

    public interface OnItemClickListener{
        void onClick(int position);
    }

    public PantryAdapter(
            ArrayList<PantryItem> pantryList,
            OnItemClickListener editListener,
            OnItemClickListener deleteListener
    ){
        this.pantryList = pantryList;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }
    @NonNull
            @Override
            public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry,parent,false);

        return new PantryViewHolder(itemView);
    }
    @Override
            public void onBindViewHolder(@NonNull PantryViewHolder holder, int position){
        PantryItem item = pantryList.get(position);
        holder.textName.setText(item.getName());
        holder.textQuantity.setText("Quantity:"+item.getQuantity());

        String unit = item.getUnit();
        holder.textUnit.setText(unit== null || unit.isEmpty() ? "Unit: Not specified":"Unit:"+unit);
        String expiry = item.getExpiryDate();
        holder.textExpiry.setText(expiry == null || expiry.isEmpty()?"Expires:Not specified":"Expires:"+expiry);

        holder.btnEdit.setOnClickListener(v->{
            int currentPosition = holder.getBindingAdapterPosition();

            if(currentPosition != RecyclerView.NO_POSITION){
                editListener.onClick(currentPosition);
            }
        });
        holder.btnDelete.setOnClickListener(v->{
            int currentPosition = holder.getBindingAdapterPosition();

            if(currentPosition != RecyclerView.NO_POSITION){
                deleteListener.onClick(currentPosition);
            }
        });
    }
    @Override
            public int getItemCount(){
        return pantryList.size();
    }
    public static class PantryViewHolder extends RecyclerView.ViewHolder {


        TextView textName;
        TextView textQuantity;
        TextView textExpiry;
TextView textUnit;
        ImageButton btnEdit;
        ImageButton btnDelete;

        public PantryViewHolder(@NonNull View itemView){
            super(itemView);

            textName =itemView.findViewById(R.id.textName);
            textQuantity = itemView.findViewById(R.id.textQuantity);
            textUnit = itemView.findViewById(R.id.textUnit);
            textExpiry = itemView.findViewById(R.id.textExpiry);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }


    }
}
