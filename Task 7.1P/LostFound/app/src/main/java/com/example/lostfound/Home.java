package com.example.lostfound;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private DBHelper db;
    private ArrayList<String> displayList;
    private ArrayList<String> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        db = new DBHelper(this);

        ListView list = findViewById(R.id.list);
        displayList = new ArrayList<>();
        itemList = new ArrayList<>();

        loadItems();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        list.setAdapter(adapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = itemList.get(position);

            Intent intent = new Intent(Home.this, Details.class);
            intent.putExtra("name", selectedItem);
            startActivity(intent);
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadItems() {
        Cursor cursor = db.getItems();
        displayList.clear();
        itemList.clear();

        if (cursor != null && cursor.moveToFirst()) {
            int typeIndex = cursor.getColumnIndex("type");
            int nameIndex = cursor.getColumnIndex("name");

            if (nameIndex == -1) {
                cursor.close();
                return;
            }
            do {
                String type = cursor.getString(typeIndex);
                String name = cursor.getString(nameIndex);

                displayList.add(type + " " + name);
                itemList.add(name);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
