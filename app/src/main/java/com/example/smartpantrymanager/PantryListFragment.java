package com.example.smartpantrymanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PantryListFragment extends Fragment {

    private final ArrayList<PantryItem> pantryList = new ArrayList<>();
    private PantryAdapter adapter;
    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pantry_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DatabaseHelper(requireContext());

        RecyclerView rv = view.findViewById(R.id.ViewPantry);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PantryAdapter(pantryList, this::showAddEditDialog, this::deleteItem);
        rv.setAdapter(adapter);

        view.findViewById(R.id.btn_add_ingredient)
                .setOnClickListener(v -> showAddEditDialog(-1));

        ((Button) view.findViewById(R.id.btn_get_recipes)).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), SuggestedRecipes.class)));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPantry();   // refreshes when returning from AddItemsActivity
    }

    private void loadPantry() {
        pantryList.clear();
        Cursor c = db.showData();
        while (c.moveToNext()) {
            PantryItem item = new PantryItem(c.getString(1), c.getDouble(2),
                    c.getString(3), c.getString(4));
            item.setId(c.getInt(0));
            pantryList.add(item);
        }
        c.close();
        adapter.notifyDataSetChanged();
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
            new DatePickerDialog(requireContext(), (p, y, m, d) ->
                    editExpiry.setText(String.format(Locale.US, "%04d-%02d-%02d", y, m + 1, d)),
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
                    String unit = editUnit.getText().toString().trim();
                    String expiry = editExpiry.getText().toString().trim();

                    if (position == -1) {
                        db.insertData(name, qty, unit, expiry);
                    } else {
                        db.updateItem(pantryList.get(position).getId(), name, qty, unit, expiry);
                    }
                    loadPantry();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void deleteItem(int position) {
        db.deleteData(pantryList.get(position).getId());
        loadPantry();
    }

    private void updateSummary() {
        View v = getView();
        if (v == null) return;

        ((TextView) v.findViewById(R.id.tvTotalItems))
                .setText(String.valueOf(pantryList.size()));

        int expiring = 0;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        fmt.setLenient(false);

        Calendar todayCal = Calendar.getInstance();
        todayCal.set(Calendar.HOUR_OF_DAY, 0);
        todayCal.set(Calendar.MINUTE, 0);
        todayCal.set(Calendar.SECOND, 0);
        todayCal.set(Calendar.MILLISECOND, 0);
        long todayMs = todayCal.getTimeInMillis();

        for (PantryItem item : pantryList) {
            String e = item.getExpiryDate();
            if (e != null && !e.isEmpty()) {
                try {
                    Date expiry = fmt.parse(e);
                    long days = TimeUnit.MILLISECONDS.toDays(expiry.getTime() - todayMs);
                    if (days <= 3) expiring++;
                } catch (ParseException ignored) { }
            }
        }

        ((TextView) v.findViewById(R.id.tvExpiringSoon)).setText(String.valueOf(expiring));
        v.findViewById(R.id.tvEmptyPantry)
                .setVisibility(pantryList.isEmpty() ? View.VISIBLE : View.GONE);
    }
}