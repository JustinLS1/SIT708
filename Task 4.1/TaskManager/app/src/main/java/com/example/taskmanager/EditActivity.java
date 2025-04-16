package com.example.taskmanager;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    private Spinner taskSpinner;
    private EditText nameInput, descInput, dayInput, monthInput, yearInput;
    private TextView editResult;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dbHelper = new DBHelper(this);
        taskSpinner = findViewById(R.id.task_spinner);
        nameInput = findViewById(R.id.name_input);
        descInput = findViewById(R.id.description_input);
        dayInput = findViewById(R.id.day);
        monthInput = findViewById(R.id.month);
        yearInput = findViewById(R.id.year);
        Button editButton = findViewById(R.id.editButton);
        Button backButton = findViewById(R.id.backButton);
        editResult = findViewById(R.id.edit_result);

        loadTasks();

        taskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTask = (String) parent.getItemAtPosition(position);
                loadTaskDetails(selectedTask);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                clearInputs();
            }
        });

        editButton.setOnClickListener(v -> updateTask());

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

    private void loadTasks() {
        ArrayList<String> taskList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllTasks("ASC");
        while (cursor.moveToNext()) {
            taskList.add(cursor.getString(0));
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, taskList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskSpinner.setAdapter(adapter);
    }

    private void loadTaskDetails(String taskName) {
        Cursor cursor = dbHelper.getAllTasks("ASC");
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(taskName)) {
                nameInput.setText(cursor.getString(0));
                descInput.setText(cursor.getString(1));
                dayInput.setText(String.valueOf(cursor.getInt(2)));
                monthInput.setText(String.valueOf(cursor.getInt(3)));
                yearInput.setText(String.valueOf(cursor.getInt(4)));
                break;
            }
        }
        cursor.close();
    }

    @SuppressLint("SetTextI18n")
    private void updateTask() {
        String oldName = nameInput.getText().toString();
        String newName = nameInput.getText().toString();
        String description = descInput.getText().toString();
        int day = Integer.parseInt(dayInput.getText().toString());
        int month = Integer.parseInt(monthInput.getText().toString());
        int year = Integer.parseInt(yearInput.getText().toString());

        if (day < 1 || day > 31) {
            editResult.setText("Day must be between 1 and 31");
            return;
        }
        if (month < 1 || month > 12) {
            editResult.setText("Month must be between 1 and 12");
            return;
        }
        if (year < 2025) {
            editResult.setText("Year must be 2025 or later");
            return;
        }

        Cursor cursor = dbHelper.getAllTasks("ASC");
        boolean nameExists = false;
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(newName) && !newName.equals(oldName)) {
                nameExists = true;
                break;
            }
        }
        cursor.close();

        if (nameExists) {
            editResult.setText("Task name already exists");
        } else {
            boolean isUpdated = dbHelper.updateTaskData(oldName, newName, description, day, month, year);
            if (isUpdated) {
                editResult.setText("Task updated " + newName + " successfully");
                setResult(RESULT_OK);
            } else {
                editResult.setText("Edits are only for description & date");
            }
        }
    }

    private void clearInputs() {
        nameInput.setText("");
        descInput.setText("");
        dayInput.setText("");
        monthInput.setText("");
        yearInput.setText("");
    }
}