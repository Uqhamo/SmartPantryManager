package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String Database_Name = "pantrydb";

    private static final String Table_Name = "pantrytb";
    private static final String Col_1 = "id";
    private static final String Col_2 = "ingredient";
    private static final String Col_3 = "qty";
    private static final String Col_4 = "unit";
    private static final String Col_5 = "expiry";

    private static final String Recipe_Table = "recipetb";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_Name, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Table_Name + "(" +
                Col_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Col_2 + " TEXT NOT NULL, " +
                Col_3 + " REAL NOT NULL, " +
                Col_4 + " TEXT, " +
                Col_5 + " TEXT)");

        db.execSQL("CREATE TABLE " + Recipe_Table + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "ingredients TEXT NOT NULL, " +
                "steps TEXT)");

        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        db.execSQL("DROP TABLE IF EXISTS " + Recipe_Table);
        onCreate(db);
    }

    private void seedRecipes(SQLiteDatabase db) {
        addSeed(db, "Scrambled Eggs", "eggs, butter, salt",
                "1. Whisk the eggs with a pinch of salt.\n2. Melt the butter in a pan.\n3. Pour in the eggs and stir gently until set.");
        addSeed(db, "Cheese Toast", "bread, cheese, butter",
                "1. Butter the bread.\n2. Top with cheese.\n3. Grill until golden.");
        addSeed(db, "Pancakes", "flour, eggs, milk, sugar, butter",
                "1. Whisk the flour, eggs, milk and sugar into a smooth batter.\n2. Melt a little butter in a pan.\n3. Pour in a ladle of batter and cook until bubbles form.\n4. Flip and cook until golden.");
        addSeed(db, "Tomato Pasta", "pasta, tomato, onion, garlic, oil",
                "1. Boil the pasta until tender, then drain.\n2. Fry the chopped onion and garlic in oil.\n3. Add the chopped tomato and simmer for 10 minutes.\n4. Toss the pasta in the sauce.");
        addSeed(db, "Egg Fried Rice", "rice, eggs, onion, oil, salt",
                "1. Cook the rice and let it cool.\n2. Fry the chopped onion in oil.\n3. Push the onion aside, scramble the eggs in the pan.\n4. Add the rice and salt, and stir-fry for 3 minutes.");
        addSeed(db, "Mashed Potatoes", "potato, butter, milk, salt",
                "1. Peel and boil the potatoes until soft.\n2. Drain and return to the pot.\n3. Add the butter, milk and salt.\n4. Mash until smooth.");
        addSeed(db, "Banana Oatmeal", "oats, milk, banana, sugar",
                "1. Heat the milk in a pot.\n2. Stir in the oats and cook for 5 minutes.\n3. Slice the banana on top.\n4. Sprinkle with sugar.");
        addSeed(db, "Cheese Omelette", "eggs, cheese, butter, salt",
                "1. Beat the eggs with a pinch of salt.\n2. Melt the butter in a pan.\n3. Pour in the eggs and cook until almost set.\n4. Add the grated cheese, fold and serve.");
        addSeed(db, "Garlic Bread", "bread, butter, garlic, salt",
                "1. Mix softened butter with crushed garlic and a pinch of salt.\n2. Spread on the bread.\n3. Bake at 180°C for 10 minutes until crisp.");
        addSeed(db, "Tuna Mayo Sandwich", "bread, tuna, mayonnaise, salt",
                "1. Drain the tuna.\n2. Mix with the mayonnaise and a pinch of salt.\n3. Spread on a slice of bread and top with another.");
        addSeed(db, "Chicken Stir Fry", "chicken, carrot, onion, oil, salt",
                "1. Slice the chicken, carrot and onion.\n2. Heat the oil in a pan.\n3. Cook the chicken until browned.\n4. Add the vegetables and salt, and stir-fry for 5 minutes.");
        addSeed(db, "Roast Potatoes", "potato, oil, salt, pepper",
                "1. Heat the oven to 200°C.\n2. Cut the potatoes into chunks and toss with oil, salt and pepper.\n3. Roast for 40 minutes, turning once.");
        addSeed(db, "Cheesy Pasta", "pasta, cheese, butter, milk",
                "1. Boil the pasta and drain.\n2. Melt the butter with the milk in a pot.\n3. Stir in the grated cheese until melted.\n4. Add the pasta and mix.");
        addSeed(db, "Beans on Toast", "beans, bread, butter",
                "1. Toast the bread and butter it.\n2. Warm the beans in a pot for 5 minutes.\n3. Spoon the beans over the toast.");
        addSeed(db, "French Toast", "bread, eggs, milk, sugar, butter",
                "1. Whisk the eggs, milk and sugar.\n2. Dip the bread slices in the mixture.\n3. Fry in butter until golden on both sides.");
        addSeed(db, "Tomato Soup", "tomato, onion, garlic, oil, salt",
                "1. Fry the chopped onion and garlic in oil.\n2. Add the chopped tomatoes and salt.\n3. Simmer for 20 minutes.\n4. Blend until smooth.");
        addSeed(db, "Lemon Garlic Chicken", "chicken, lemon, garlic, oil, salt",
                "1. Mix the lemon juice, crushed garlic, oil and salt.\n2. Coat the chicken and rest for 15 minutes.\n3. Bake at 200°C for 30 minutes until cooked through.");
        addSeed(db, "Rice Pudding", "rice, milk, sugar, butter",
                "1. Combine the rice and milk in a pot.\n2. Simmer on low for 30 minutes, stirring often.\n3. Stir in the sugar and butter.");
        addSeed(db, "Carrot Soup", "carrot, onion, potato, butter, salt",
                "1. Chop the carrot, onion and potato.\n2. Soften the onion in butter.\n3. Add the other vegetables, salt and enough water to cover.\n4. Simmer for 25 minutes, then blend.");
        addSeed(db, "Garlic Butter Rice", "rice, garlic, butter, salt",
                "1. Cook the rice and keep warm.\n2. Melt the butter and fry the crushed garlic for 1 minute.\n3. Stir through the rice with a pinch of salt.");
    }

    private void addSeed(SQLiteDatabase db, String name, String ingredients, String steps) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("ingredients", ingredients);
        cv.put("steps", steps);
        db.insert(Recipe_Table, null, cv);
    }

    public boolean insertData(String ingredient, double qty, String unit, String expiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Col_2, ingredient);
        cv.put(Col_3, qty);
        cv.put(Col_4, unit);
        cv.put(Col_5, expiry);
        return db.insert(Table_Name, null, cv) != -1;
    }

    public boolean insertRecipe(String name, String ingredients, String steps) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("ingredients", ingredients);
        cv.put("steps", steps);
        return getWritableDatabase().insert(Recipe_Table, null, cv) != -1;
    }

    public boolean checkRecipe(String ingredient) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT 1 FROM " + Table_Name + " WHERE " + Col_2 + " = ? COLLATE NOCASE",
                new String[]{ingredient});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Cursor showData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + Table_Name, null);
    }

    public Cursor searchUser(String ingredient) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + Table_Name + " WHERE " + Col_2 + " LIKE ?",
                new String[]{"%" + ingredient + "%"});
    }

    public boolean updateItem(int id, String ingredient, double qty, String unit, String expiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Col_2, ingredient);
        cv.put(Col_3, qty);
        cv.put(Col_4, unit);
        cv.put(Col_5, expiry);
        return db.update(Table_Name, cv, Col_1 + " = ?",
                new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table_Name, Col_1 + " = ?",
                new String[]{String.valueOf(id)}) > 0;
    }

    public ArrayList<String[]> getSuggestedRecipes() {
        SQLiteDatabase db = getReadableDatabase();

        HashSet<String> have = new HashSet<>();
        Cursor p = db.rawQuery("SELECT " + Col_2 + " FROM " + Table_Name, null);
        while (p.moveToNext()) have.add(p.getString(0).trim().toLowerCase());
        p.close();

        ArrayList<String[]> result = new ArrayList<>();
        Cursor r = db.rawQuery("SELECT name, ingredients, steps FROM " + Recipe_Table, null);
        while (r.moveToNext()) {
            boolean canCook = true;
            for (String needed : r.getString(1).split(",")) {
                if (!have.contains(needed.trim().toLowerCase())) { canCook = false; break; }
            }
            if (canCook) {
                result.add(new String[]{r.getString(0), r.getString(1),
                        r.isNull(2) ? "" : r.getString(2)});
            }
        }
        r.close();
        return result;
    }
}

