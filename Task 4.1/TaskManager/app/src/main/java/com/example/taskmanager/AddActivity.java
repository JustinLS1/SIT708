package com.example.taskmanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddActivity extends AppCompatActivity {

    private EditText nameInput, descInput, dayInput, monthInput, yearInput;
    private TextView addResult;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameInput = findViewById(R.id.name_input);
        descInput = findViewById(R.id.description_input);
        dayInput = findViewById(R.id.day);
        monthInput = findViewById(R.id.month);
        yearInput = findViewById(R.id.year);
        Button addButton = findViewById(R.id.addButton);
        Button backButton = findViewById(R.id.backButton);
        addResult = findViewById(R.id.add_result);
        dbHelper = new DBHelper(this);

        addButton.setOnClickListener(v -> addTask());

        backButton.setOnClickListener(v -> finish());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_exit) {
                finishAffinity();
                return true;
            }
            return false;
        });
    }

    @SuppressLint("SetTextI18n")
    private void addTask() {
        String name = nameInput.getText().toString().trim();
        String desc = descInput.getText().toString().trim();
        String day = dayInput.getText().toString().trim();
        String month = monthInput.getText().toString().trim();
        String year = yearInput.getText().toString().trim();

        if (name.isEmpty() || desc.isEmpty() || day.isEmpty() || month.isEmpty() || year.isEmpty()) {
            addResult.setText("Please fill in the fields");
            return;
        }

        int day1 = Integer.parseInt(day);
        int month1 = Integer.parseInt(month);
        int year1 = Integer.parseInt(year);

        if (day1 < 1 || day1 > 31) {
            addResult.setText("Day must be between 1 and 31");
            return;
        }
        if (month1 < 1 || month1 > 12) {
            addResult.setText("Month must be between 1 and 12");
            return;
        }
        if (year1 < 2025) {
            addResult.setText("Year must be 2025 or later");
            return;
        }

        boolean isInserted = dbHelper.insertTaskData(name, desc, day1, month1, year1);

        if (isInserted) {
            addResult.setText("Task added successfully");
            setResult(RESULT_OK);
        } else {
            addResult.setText("Task name may exist already");
        }
    }
}