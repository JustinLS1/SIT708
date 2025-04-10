package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText input1, input2;
    private TextView results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input1 = findViewById(R.id.num1);
        input2 = findViewById(R.id.num2);
        results = findViewById(R.id.results);

        Button addButton = findViewById(R.id.add);
        addButton.setOnClickListener(v -> Addition());

        Button subtractButton = findViewById(R.id.subtract);
        subtractButton.setOnClickListener(v -> Subtraction());
    }

    private void Addition() {
        double num1 = Double.parseDouble(input1.getText().toString());
        double num2 = Double.parseDouble(input2.getText().toString());
        int result = (int) (num1 + num2);
        results.setText(String.valueOf(result));
    }

    private void Subtraction() {
        double num1 = Double.parseDouble(input1.getText().toString());
        double num2 = Double.parseDouble(input2.getText().toString());
        int result = (int) (num1 - num2);
        results.setText(String.valueOf(result));
    }
}