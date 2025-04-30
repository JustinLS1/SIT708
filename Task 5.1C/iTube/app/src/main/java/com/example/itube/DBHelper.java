package com.example.itube;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "userdata.db", null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE userdetails(fullname TEXT, username TEXT PRIMARY KEY, password TEXT)");
        DB.execSQL("CREATE TABLE playlists(username TEXT, URL TEXT, PRIMARY KEY(username, URL))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS playlists");
        DB.execSQL("DROP TABLE IF EXISTS userdetails");
        onCreate(DB);
    }

    public boolean addUser (String full, String username, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", full);
        contentValues.put("username", username);
        contentValues.put("password", password);

        long result = DB.insert("userdetails", null, contentValues);
        DB.close();
        return result != -1;
    }

    public boolean checkUser (String username, String password) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM userdetails WHERE username = ? AND password = ?", new String[]{username, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean addURL(String username, String URL) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("URL", URL);

        long result = DB.insert("playlists", null, contentValues);
        DB.close();
        return result != -1;
    }

    public Cursor getPlaylist (String username) {
        SQLiteDatabase DB = this.getReadableDatabase();
        return DB.rawQuery("SELECT URL FROM playlists WHERE username = ?", new String[]{username});
    }
}