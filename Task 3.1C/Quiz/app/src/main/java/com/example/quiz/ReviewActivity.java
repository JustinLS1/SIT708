package com.example.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ReviewActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        TextView selectedAnswerText = findViewById(R.id.selected);
        TextView correctAnswerText = findViewById(R.id.correct);
        TextView currentQuestionText = findViewById(R.id.current);
        TextView questionNumber = findViewById(R.id.questionnumber);
        Button buttonNext = findViewById(R.id.buttonnext);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        String selectedAnswer = intent.getStringExtra("SELECTED");
        String correctAnswer = intent.getStringExtra("CORRECT");
        String currentQuestion = intent.getStringExtra("CURRENT");
        int totalQuestions = intent.getIntExtra("TOTAL", 0);
        int currents = intent.getIntExtra("CURRENTS", 0);
        int progress = intent.getIntExtra("PROGRESS", 0);
        int score = intent.getIntExtra("SCORE", 0);

        selectedAnswerText.setText("Your Answer: " + selectedAnswer);
        correctAnswerText.setText("Correct Answer: " + correctAnswer);
        currentQuestionText.setText(currentQuestion);
        questionNumber.setText((currents + 1) + "/" + totalQuestions);

        progressBar.setProgress(progress);

        assert selectedAnswer != null;
        if (selectedAnswer.equals(correctAnswer)) {
            selectedAnswerText.setBackgroundColor(ContextCompat.getColor(this, R.color.correct));
        } else {
            selectedAnswerText.setBackgroundColor(ContextCompat.getColor(this, R.color.incorrect));
            correctAnswerText.setBackgroundColor(ContextCompat.getColor(this, R.color.correct));
        }

        buttonNext.setOnClickListener(v -> {
            if (currents + 1 < totalQuestions) {
                Intent nextIntent = new Intent(ReviewActivity.this, QuizActivity.class);
                nextIntent.putExtra("NEXT", currents + 1);
                nextIntent.putExtra("USERNAME", intent.getStringExtra("USERNAME"));
                nextIntent.putExtra("SCORE", score);
                startActivity(nextIntent);
                finish();
            } else {
                Intent resultIntent = new Intent(ReviewActivity.this, ResultActivity.class);
                resultIntent.putExtra("SCORE", score);
                resultIntent.putExtra("TOTAL", totalQuestions);
                resultIntent.putExtra("USERNAME", intent.getStringExtra("USERNAME"));
                startActivity(resultIntent);
                finish();
            }
        });
    }
}