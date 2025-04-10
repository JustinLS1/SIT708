package com.example.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView nameInput = findViewById(R.id.nameinput);
        TextView results = findViewById(R.id.results);
        Button buttonReset = findViewById(R.id.buttonreset);
        Button buttonExit = findViewById(R.id.buttonexit);

        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        int totalQuestions = intent.getIntExtra("TOTAL", 0);
        String username = intent.getStringExtra("USERNAME");

        nameInput.setText(username);
        results.setText(score + "/" + totalQuestions);

        buttonReset.setOnClickListener(v -> {
            Intent intent1 = new Intent(ResultActivity.this, MainActivity.class);
            intent1.putExtra("USERNAME", username);
            startActivity(intent1);
            finish();
        });

        buttonExit.setOnClickListener(v -> finishAffinity());
    }
}