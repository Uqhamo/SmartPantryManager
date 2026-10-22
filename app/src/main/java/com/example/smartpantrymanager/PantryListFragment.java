package com.example.smartpantrymanager;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;



public class PantryListFragment extends Fragment {
private ArrayList<PantryItem>pantryList;
private PantryAdapter adapter;


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

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pantryList = new ArrayList<>();
        RecyclerView rv = view.findViewById(R.id.ViewPantry);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PantryAdapter(pantryList, this::showAddEditDialog, this::deleteItem);
        rv.setAdapter(adapter);

        view.findViewById(R.id.btn_add_ingredient)
                .setOnClickListener(v -> showAddEditDialog(-1));

        updateSummary();
    }

    private void showAddEditDialog(int position) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.add_edit, null);

        EditText editName = dialogView.findViewById(R.id.editName);
        EditText editQuantity = dialogView.findViewById(R.id.editQuantity);
        EditText editUnit = dialogView.findViewById(R.id.editUnit);
        EditText editExpiry = dialogView.findViewById(R.id.editExpiry);

        editExpiry.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (picker, year, month, day) ->
                    editExpiry.setText(String.format("%04d-%02d-%02d", year, month + 1, day)),
                    cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show();
        });

        if (position != -1) {
            PantryItem item = pantryList.get(position);
            editName.setText(item.getName());
            editQuantity.setText(String.valueOf(item.getQuantity()));
            editUnit.setText(item.getUnit());
            editExpiry.setText(item.getExpiryDate());
        }

        new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle(position == -1 ? "Add Pantry Item" : "Edit Pantry Item")
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = editName.getText().toString().trim();
                    String qtyText = editQuantity.getText().toString().trim();
                    if (name.isEmpty() || qtyText.isEmpty()) {
                        Toast.makeText(requireContext(), "Name and quantity are required",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double qty = Double.parseDouble(qtyText);
                    String unit = editUnit.getText().toString();
                    String expiry = editExpiry.getText().toString();

                    if (position == -1) {
                        pantryList.add(new PantryItem(name, qty, unit, expiry));
                        adapter.notifyItemInserted(pantryList.size() - 1);
                    } else {
                        PantryItem item = pantryList.get(position);
                        item.setName(name);
                        item.setQuantity(qty);
                        item.setUnit(unit);
                        item.setExpiryDate(expiry);
                        adapter.notifyItemChanged(position);
                    }
                    updateSummary();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void deleteItem(int position) {
        pantryList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, pantryList.size() - position);
        updateSummary();
    }

    private void updateSummary() {
        View v = requireView();
        ((TextView) v.findViewById(R.id.tvTotalItems)).setText(String.valueOf(pantryList.size()));

        int expiring = 0;
        LocalDate today = LocalDate.now();
        for (PantryItem item : pantryList) {
            String e = item.getExpiryDate();
            if (e != null && !e.isEmpty()) {
                try {
                    long days = ChronoUnit.DAYS.between(today, LocalDate.parse(e));
                    if (days <= 3) expiring++;
                } catch (DateTimeParseException ignored) { }
            }
        }
        ((TextView) v.findViewById(R.id.tvExpiringSoon)).setText(String.valueOf(expiring));
        v.findViewById(R.id.tvEmptyPantry)
                .setVisibility(pantryList.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
