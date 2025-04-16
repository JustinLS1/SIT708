package com.example.taskmanager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "taskdata.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE Taskdetails(name TEXT PRIMARY KEY, description TEXT, day INTEGER, month INTEGER, year INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS Taskdetails");
        onCreate(DB);
    }

    public boolean insertTaskData(String name, String description, int day, int month, int year) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("day", day);
        contentValues.put("month", month);
        contentValues.put("year", year);

        long result = DB.insert("Taskdetails", null, contentValues);
        return result != -1;
    }

    public boolean updateTaskData(String oldName, String newName, String description, int day, int month, int year) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", newName);
        contentValues.put("description", description);
        contentValues.put("day", day);
        contentValues.put("month", month);
        contentValues.put("year", year);

        @SuppressLint("Recycle") Cursor cursor = DB.rawQuery("SELECT * FROM Taskdetails WHERE name = ?", new String[]{oldName});
        if (cursor.getCount() > 0) {
            long result = DB.update("Taskdetails", contentValues, "name=?", new String[]{oldName});
            return result != -1;
        } else {
            return false;
        }
    }

    public boolean deleteTaskData(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = DB.rawQuery("SELECT * FROM Taskdetails WHERE name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Taskdetails", "name=?", new String[]{name});
            return result != -1;
        } else {
            return false;
        }
    }

    public Cursor getAllTasks(String order) {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("SELECT * FROM " + "Taskdetails" + " ORDER BY year " + order + ", month " + order + ", day " + order, null);
    }
}