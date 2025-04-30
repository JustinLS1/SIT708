package com.example.itube;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Playlist extends Activity {

    private ListView list;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);

        list = findViewById(R.id.list);
        dbHelper = new DBHelper(this);
        String username = getIntent().getStringExtra("USERNAME");

        loadPlaylist(username);
    }

    private void loadPlaylist(String username) {
        Cursor cursor = dbHelper.getPlaylist(username);

        List<String> playlist = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlist);
        list.setAdapter(adapter);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int URLIndex = cursor.getColumnIndex("URL");

                if (URLIndex != -1) {
                    do {
                        String URL = cursor.getString(URLIndex);
                        playlist.add(URL);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
        }
    }
}