package com.example.smartpantrymanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
private ArrayList<PantryItem> pantryList;
private PantryAdapter adapter;
DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
 NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager()
         .findFragmentById(R.id.nav_host_fragment);
 BottomNavigationView bottonNav = findViewById(R.id.bottomNav);
        NavigationUI.setupWithNavController(bottonNav,navHost.getNavController());
        pantryList = new ArrayList<>();
        RecyclerView rv = findViewById(R.id.ViewPantry);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PantryAdapter(pantryList,this::showAddEditDialog, this::deleteItem);
        rv.setAdapter(adapter);

        fabAdd.setOnClickListener(v->showAddEditDialog(-1));
    }
    private void showAddEditDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.add_edit,null);
        builder.setView(dialogView);

        EditText editName = dialogView.findViewById(R.id.editName);
        EditText editQuantity = dialogView.findViewById(R.id.editQuantity);
        EditText editUnit = dialogView.findViewById(R.id.editUnit);
        EditText editExpiry = dialogView.findViewById(R.id.editExpiry);
    editExpiry.setOnClickListener(v ->{
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(this,(view, year, month, dayOfMonth) ->
                   editExpiry.setText(String.format("%04d-%02d-%02D",year,month+1,dayOfMonth)),cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show();
    }
    );
            if(position !=1){
                PantryItem item = pantryList.get(position);
                editName.setText(item.getName());
                editQuantity.setText(String.valueOf(item.getQuantity()));
                editUnit.setText(item.getUnit());
                editExpiry.setText(item.getExpiryDate());
            }

            builder.setTitle(position == -1?"Add Pantry Item":"Edit Pantry Item ").setPositiveButton("Save",(dialog, which) ->{
                String name = editName.getText().toString().trim();
                String qtyText = editQuantity.getText().toString().trim();
                if (name.isEmpty() || qtyText.isEmpty()) {
                    Toast.makeText(this,"Name and quatity are required",Toast.LENGTH_SHORT).show();
                return;
                }
                double qty = Double.parseDouble(qtyText);

                String unit = editUnit.getText().toString();
                String expiry = editExpiry.getText().toString();

                if(position ==-1){
                    pantryList.add(new PantryItem(name,qty,unit,expiry));
                    adapter.notifyItemInserted(pantryList.size() -1);

                }else {
                    PantryItem item = pantryList.get(position);
                    item.setName(name);
                    item.setQuantity(qty);
                    item.setUnit(unit);
                    item.setExpiryDate(expiry);
                    adapter.notifyItemChanged(position);
                }}).setNegativaButton("Cancel",null).show();
            }
             public  void deleteItem(int position){
        pantryList.remove(position);
        adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position,pantryList.size() - position);

            }

    }
}