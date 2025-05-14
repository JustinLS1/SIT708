package com.example.lostfound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Advert extends AppCompatActivity {

    private EditText nameInput, phoneInput, descInput, dateInput, locationInput;
    private RadioGroup radioType;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advert);

        db = new DBHelper(this);

        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        descInput = findViewById(R.id.descInput);
        dateInput = findViewById(R.id.dateInput);
        locationInput = findViewById(R.id.locationInput);
        radioType = findViewById(R.id.radioType);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveData());
    }

    private void saveData() {
        String name = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String description = descInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();

        int selectedId = radioType.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        String type = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";

        if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty() || type.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = db.addItem(type, name, phone, description, date, location);
        if (isInserted) {
            Intent intent = new Intent(Advert.this, Home.class);
            startActivity(intent);
            Toast.makeText(this, "Item posted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to post item", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(Advert.this, Home.class);
        startActivity(intent);
    }
}
