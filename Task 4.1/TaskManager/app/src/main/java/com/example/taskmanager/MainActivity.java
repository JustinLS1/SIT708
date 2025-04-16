package com.example.taskmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private TextView task;
    private Spinner dateSpinner;
    private ArrayList<String> taskList;
    private ArrayAdapter<String> adapter;
    private DBHelper dbHelper;

    private final ActivityResultLauncher<Intent> addActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadTasks(dateSpinner.getSelectedItemPosition() == 0 ? "ASC" : "DESC");
                }
            });

    private final ActivityResultLauncher<Intent> editActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadTasks(dateSpinner.getSelectedItemPosition() == 0 ? "ASC" : "DESC");
                }
            });

    private final ActivityResultLauncher<Intent> deleteActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadTasks(dateSpinner.getSelectedItemPosition() == 0 ? "ASC" : "DESC");
                }
            });

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        task = findViewById(R.id.task_list);
        dateSpinner = findViewById(R.id.dateSpinner);
        dbHelper = new DBHelper(this);
        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        list.setAdapter(adapter);

        loadTasks("ASC");

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(spinnerAdapter);

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String order = position == 0 ? "ASC" : "DESC";
                loadTasks(order);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        findViewById(R.id.addButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            addActivityLauncher.launch(intent);
        });

        findViewById(R.id.editButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            editActivityLauncher.launch(intent);
        });

        findViewById(R.id.deleteButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
            deleteActivityLauncher.launch(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                return true;
            } else if (item.getItemId() == R.id.nav_exit) {
                finishAffinity();
                return true;
            }
            return false;
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadTasks(String order) {
        Cursor cursor = dbHelper.getAllTasks(order);
        taskList.clear();

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("name");
            int descriptionIndex = cursor.getColumnIndex("description");
            int dayIndex = cursor.getColumnIndex("day");
            int monthIndex = cursor.getColumnIndex("month");
            int yearIndex = cursor.getColumnIndex("year");

            if (nameIndex == -1 || descriptionIndex == -1 || dayIndex == -1 || monthIndex == -1 || yearIndex == -1) {
                cursor.close();
                return;
            }

            do {
                String name = cursor.getString(nameIndex);
                String description = cursor.getString(descriptionIndex);
                int day = cursor.getInt(dayIndex);
                int month = cursor.getInt(monthIndex);
                int year = cursor.getInt(yearIndex);

                @SuppressLint("DefaultLocale") String taskDetails = String.format("\nName: %s\n\nDescription: %s\n\nDue Date: %02d/%02d/%04d\n", name, description, day, month, year);
                taskList.add(taskDetails);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            assert cursor != null;
            cursor.close();
        }

        if (taskList.isEmpty()) {
            task.setText("(Empty)");
            task.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        } else {
            task.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadTasks(dateSpinner.getSelectedItemPosition() == 0 ? "ASC" : "DESC");
        }
    }
}