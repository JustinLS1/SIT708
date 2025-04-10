package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameinput);
        Button submitButton = findViewById(R.id.submitButton);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        submitButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            if (!name.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("USERNAME", name);
                startActivity(intent);
            } else {
                nameInput.setError("Please enter your name");
            }
        });
    }
}