package com.example.lostfound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "itemlist.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE Itemdetails(type TEXT, name TEXT PRIMARY KEY, phone TEXT, description TEXT, date TEXT, location TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS Itemdetails");
        onCreate(DB);
    }

    public boolean addItem(String type, String name, String phone, String description, String date, String location) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", type);
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("description", description);
        contentValues.put("date", date);
        contentValues.put("location", location);

        long result = DB.insert("Itemdetails", null, contentValues);
        return result != -1;
    }

    public Cursor getItems() {
        SQLiteDatabase DB = this.getReadableDatabase();
        return DB.rawQuery("SELECT * FROM Itemdetails", null);
    }

    public Cursor getItemByName(String name) {
        SQLiteDatabase DB = this.getReadableDatabase();
        return DB.rawQuery("SELECT * FROM Itemdetails WHERE name = ?", new String[]{name});
    }

    public boolean removeItem(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Itemdetails WHERE name = ?", new String[]{name});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        if (exists) {
            long result = DB.delete("Itemdetails", "name=?", new String[]{name});
            return result != -1;
        } else {
            return false;
        }
    }
}