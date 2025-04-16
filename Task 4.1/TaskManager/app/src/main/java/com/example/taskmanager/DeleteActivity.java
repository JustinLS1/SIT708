package com.example.taskmanager;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {

    private Spinner taskSpinner;
    private ArrayList<String> taskList;
    private TextView deleteResult;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        taskSpinner = findViewById(R.id.task_spinner);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button backButton = findViewById(R.id.backButton);
        deleteResult = findViewById(R.id.delete_result);
        taskList = new ArrayList<>();
        dbHelper = new DBHelper(this);

        loadTasks();

        deleteButton.setOnClickListener(v -> deleteSelectedTask());

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
        Cursor cursor = dbHelper.getAllTasks("ASC");

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                taskList.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, taskList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskSpinner.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void deleteSelectedTask() {
        String selectedTask = (String) taskSpinner.getSelectedItem();
        if (selectedTask != null) {
            boolean isDeleted = dbHelper.deleteTaskData(selectedTask);

            if (isDeleted) {
                deleteResult.setText("Task " + selectedTask + " deleted successfully");
                setResult(RESULT_OK);
            } else {
                deleteResult.setText("Task '" + selectedTask + "' may have been deleted");
            }
        }
    }
}