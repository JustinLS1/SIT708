package com.example.itube;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    DBHelper db;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        EditText fullInput = findViewById(R.id.fullInput);
        EditText usernameInput = findViewById(R.id.usernameInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        EditText confirmInput = findViewById(R.id.confirmInput);
        TextView resultView = findViewById(R.id.result);
        Button createButton = findViewById(R.id.createButton);
        Button backButton = findViewById(R.id.backButton);
        db = new DBHelper(this);

        createButton.setOnClickListener(view -> {
            String full = fullInput.getText().toString().trim();
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confirmPassword = confirmInput.getText().toString().trim();

            if (full.isEmpty()) {
                resultView.setText("Please enter your full name");
                return;
            }
            if (username.isEmpty()) {
                resultView.setText("Please enter your username");
                return;
            }
            if (password.isEmpty()) {
                resultView.setText("Please enter your password");
                return;
            }
            if (confirmPassword.isEmpty()) {
                resultView.setText("Please enter your confirm password");
                return;
            }

            if (!password.equals(confirmPassword)) {
                resultView.setText("Passwords must match each other");
                return;
            }

            boolean result = db.addUser (full, username, password);
            if (result) {
                resultView.setText("Successfully added new user");
            } else {
                resultView.setText("Failed to add new user. Username may already exist");
            }
        });

        backButton.setOnClickListener(v -> finish());
    }
}
