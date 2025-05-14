package com.example.lostfound;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Details extends AppCompatActivity {

    TextView nameText, phoneText, descriptionText, dateText, locationText;
    Button removeButton;
    DBHelper db;
    String name;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        db = new DBHelper(this);

        nameText = findViewById(R.id.name);
        phoneText = findViewById(R.id.phone);
        descriptionText = findViewById(R.id.description);
        dateText = findViewById(R.id.date);
        locationText = findViewById(R.id.location);
        removeButton = findViewById(R.id.removeButton);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        Cursor cursor = db.getItemByName(name);
        if (cursor != null && cursor.moveToFirst()) {
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));

            nameText.setText(type + " " + name);
            phoneText.setText(phone);
            descriptionText.setText(description);
            dateText.setText(date);
            locationText.setText(location);

            cursor.close();
        } else {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        removeButton.setOnClickListener(v -> {
            boolean deleted = db.removeItem(name);
            if (deleted) {
                Toast.makeText(Details.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(Details.this, Home.class);
                startActivity(intent1);
                finish();
            } else {
                Toast.makeText(Details.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
