package com.example.itube;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText usernameInput = findViewById(R.id.usernameInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        TextView resultView = findViewById(R.id.result);
        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.signupButton);
        db = new DBHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override public void onClick(View view) {

                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (username.isEmpty() && password.isEmpty()) {
                    resultView.setText("Please enter your credentials");
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

                boolean result = db.checkUser (usernameInput.getText().toString(), passwordInput.getText().toString());
                if (result) {
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                } else {
                    resultView.setText("Incorrect Username or Password");
                }
            }
        });

        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        });
    }
}