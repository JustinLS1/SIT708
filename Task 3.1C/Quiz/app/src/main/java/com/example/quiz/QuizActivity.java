package com.example.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {
    private TextView question;
    private TextView questionNumber;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private ProgressBar progressBar;

    private String[] questions;
    private String[][] answers;
    private String[] correctAnswers;
    private String[] userAnswers;
    private int currentQuestion = 0;
    private int score = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        TextView welcome = findViewById(R.id.welcome);
        question = findViewById(R.id.textViewQuestion);
        questionNumber= findViewById(R.id.question_number);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        Button submitButton = findViewById(R.id.buttonSubmit);
        progressBar = findViewById(R.id.progressBar);

        questions = new String[]{
                getString(R.string.question1),
                getString(R.string.question2),
                getString(R.string.question3),
                getString(R.string.question4),
                getString(R.string.question5)
        };

        answers = new String[][]{
                {
                        getString(R.string.q1answer1),
                        getString(R.string.q1answer2),
                        getString(R.string.q1answer3)
                },
                {
                        getString(R.string.q2answer1),
                        getString(R.string.q2answer2),
                        getString(R.string.q2answer3)
                },
                {
                        getString(R.string.q3answer1),
                        getString(R.string.q3answer2),
                        getString(R.string.q3answer3)
                },
                {
                        getString(R.string.q4answer1),
                        getString(R.string.q4answer2),
                        getString(R.string.q4answer3)
                },
                {
                        getString(R.string.q5answer1),
                        getString(R.string.q5answer2),
                        getString(R.string.q5answer3)
                }
        };

        correctAnswers = new String[]{
                getString(R.string.q1answer2),
                getString(R.string.q2answer1),
                getString(R.string.q3answer2),
                getString(R.string.q4answer3),
                getString(R.string.q5answer1)
        };

        userAnswers = new String[questions.length];

        Intent intent = getIntent();
        String userName = intent.getStringExtra("USERNAME");
        if (userName != null && !userName.isEmpty()) {
            welcome.setText("Welcome " + userName + "!");
        } else {
            welcome.setText("Welcome!");
        }

        loadQuestion();

        setRadioButtonListeners();

        submitButton.setOnClickListener(v -> checkAnswer());
    }

    @SuppressLint("SetTextI18n")
    private void loadQuestion() {
        question.setText(questions[currentQuestion]);

        questionNumber.setText((currentQuestion + 1) + "/" + questions.length);

        answer1.setText(answers[currentQuestion][0]);
        answer2.setText(answers[currentQuestion][1]);
        answer3.setText(answers[currentQuestion][2]);

        answer1.setChecked(false);
        answer2.setChecked(false);
        answer3.setChecked(false);

        answer1.setBackgroundResource(R.drawable.radiobutton_design);
        answer2.setBackgroundResource(R.drawable.radiobutton_design);
        answer3.setBackgroundResource(R.drawable.radiobutton_design);

        int progress = (currentQuestion + 1) * (100 / questions.length);
        progressBar.setProgress(progress);
    }

    private void setRadioButtonListeners() {
        answer1.setOnClickListener(v -> toggleRadioButton(answer1));
        answer2.setOnClickListener(v -> toggleRadioButton(answer2));
        answer3.setOnClickListener(v -> toggleRadioButton(answer3));
    }

    private void toggleRadioButton(RadioButton radioButton) {
        answer1.setChecked(false);
        answer2.setChecked(false);
        answer3.setChecked(false);

        radioButton.setChecked(true);
    }

    private void checkAnswer() {
        String selectedAnswer = null;

        if (answer1.isChecked()) {
            selectedAnswer = answer1.getText().toString();
        } else if (answer2.isChecked()) {
            selectedAnswer = answer2.getText().toString();
        } else if (answer3.isChecked()) {
            selectedAnswer = answer3.getText().toString();
        }

        if (selectedAnswer != null) {
            userAnswers[currentQuestion] = selectedAnswer;

            if (selectedAnswer.equals(correctAnswers[currentQuestion])) {
                score++;
            }

            Intent intent = new Intent(QuizActivity.this, ReviewActivity.class);
            intent.putExtra("SELECTED", selectedAnswer);
            intent.putExtra("CORRECT", correctAnswers[currentQuestion]);
            intent.putExtra("CURRENT", questions[currentQuestion]);
            intent.putExtra("TOTAL", questions.length);
            intent.putExtra("CURRENTS", currentQuestion);

            int progress = (currentQuestion + 1) * (100 / questions.length);
            intent.putExtra("PROGRESS", progress);

            intent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
            intent.putExtra("SCORE", score);

            startActivity(intent);
        } else {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().hasExtra("NEXT")) {
            currentQuestion = getIntent().getIntExtra("NEXT", currentQuestion);
            if (currentQuestion < questions.length) {
                loadQuestion();
            } else {
                showResults();
            }
        }

        if (getIntent().hasExtra("SCORE")) {
            score = getIntent().getIntExtra("SCORE", score);
        }
    }
    private void showResults() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL", questions.length);
        intent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
        startActivity(intent);
        finish();
    }
}