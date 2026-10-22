package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String Database_Name ="pantrydb";

    private  static  final String Table_Name ="pantrytb";
    private static final String Col_1 = "id";
    private static final String Col_2 = "ingredient";
    private static final String Col_3 = "qty";
    private static final String Col_4 = "unit";
    private static final String Col_5 = "expiry";

    public DatabaseHelper(@Nullable Context context
    ) {
        super(context, Database_Name, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +Table_Name+ "(Col_1 primary key autoincrement," +
                "Col_2 text not null, " +
                "Col_3 real not null, " +
                "Col_4 text not null, " +
                "Col_5 text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
        onCreate(db);
    }

    public boolean insertData(String ingredient,int qty,String unit,String expiry){
        SQLiteDatabase db = this.getWritableDatabase();
        //adding write properties to our database
        ContentValues cv = new ContentValues();
        cv.put(Col_2,ingredient);
        cv.put(Col_3,qty);
        cv.put(Col_4,unit);
        cv.put(Col_5,expiry);

        long result = db.insert(Table_Name,null,cv);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }
    //To navigate through the rows of a database table, we use object called cursor
    public boolean checkRecipe(String ingredient){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
        "SELECT 1 FROM "+Table_Name + " WHERE "+Col_2+"= ? COLLATE NOCASE",
        new String[]{ingredient});
        boolean exists= cursor.getCount() >0;
        cursor.close();
        return exists;


    }


    public Cursor showData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name, null);
        return cursor;
    }
    public Cursor searchUser(String ingredient){
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM "+ Table_Name + " WHERE " + Col_2 + " LIKE ?", new String[] {"%"+ingredient+"%"}
        );
    }
    public boolean updateItem(int id,String ingredient, double qty,String unit, String expiry){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Col_2,ingredient);
        cv.put(Col_3,qty);
        cv.put(Col_4,unit);
        cv.put(Col_5,expiry);
        return db.update(
                Table_Name,cv,Col_1 +" ?",
                new String[]{String.valueOf(id)})> 0;}




    public boolean deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(
                Table_Name,Col_1 +" ?",
                new String[]{String.valueOf(id)})>0;}

    }



