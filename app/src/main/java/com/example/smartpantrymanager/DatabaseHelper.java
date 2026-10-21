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
    private static final String Col_2 = "ingrendent";
    private static final String Col_3 = "surname";
    private static final String Col_4 = "email";
    private static final String Col_5 = "password";

    public DatabaseHelper(@Nullable Context context
    ) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +Table_Name+ "(id integer primary key autoincrement," +
                "name text not null, " +
                "surname text not null, " +
                "email text not null, " +
                "password text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
        onCreate(db);
    }

    public boolean insertData(String name,String surname,String email,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        //adding write properties to our database
        ContentValues cv = new ContentValues();
        cv.put(Col_2,name);
        cv.put(Col_3,surname);
        cv.put(Col_4,email);
        cv.put(Col_5,pass);

        long result = db.insert(Table_Name,null,cv);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }
    //To navigate through the rows of a database table, we use object called cursor
    public boolean checkLogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+Table_Name + " WHERE "+Col_4+"= ?" +" AND " +Col_5+"=?";
        Cursor cursor = db.rawQuery(query,new String[]{email,password});
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
    public Cursor searchUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM "+ Table_Name + " WHERE " + Col_4 + " = ?", new String[] {email}
        );
    }
    public boolean updateData(String email,
                              String name,
                              String surname,

                              String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Col_2,name);
        cv.put(Col_3,surname);

        cv.put(Col_5,password);

        int result = db.update(
                Table_Name,cv,Col_4 +" ?",
                new String[]{email}
        );
        return  result >0;
    }

    public boolean deleteData(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(
                Table_Name,Col_4 +" ?",
                new String[]{email}
        );
        return  result >0;
    }
}

}
