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
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +Table_Name+ "(id integer primary key autoincrement," +
                "ingredient text not null, " +
                "qty integer not null, " +
                "uint integer not null, " +
                "expiry datetime not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
        onCreate(db);
    }

    public boolean insertData(String ingredient,int qty,int unit,int expiry){
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
    public boolean checkRecipe(String ingredient, int expiry){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+Table_Name + " WHERE "+Col_4+"= ?" +" AND " +Col_5+"=?";
        Cursor cursor = db.rawQuery(query,new String[]{ingredient,expiry});
        boolean exists= cursor.getCount() >0;
        cursor.close();
        return exists;


    }
    //if you want to return everything in the database table

    public Cursor showData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name, null);
        return cursor;
    }
    public Cursor searchUser(String ingredient){
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM "+ Table_Name + " WHERE " + Col_4 + " = ?", new String[] {ingredient}
        );
    }
    public boolean updateRecipe(String ingredient,
                              int qty,
                              int unit,

                              int expiry){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Col_2,ingredient);
        cv.put(Col_3,qty);
        cv.put(Col_4,unit);
        cv.put(Col_5,expiry);

        int result = db.update(
                Table_Name,cv,Col_2 +" ?",
                new String[]{ingredient}
        );
        return  result >0;
    }

    public boolean deleteData(String ingredient){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(
                Table_Name,Col_2 +" ?",
                new String[]{ingredient}
        );
        return  result >0;
    }
}


