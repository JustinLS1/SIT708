package com.example.lostfound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button advert = findViewById(R.id.advertButton);
        Button list = findViewById(R.id.listButton);

        advert.setOnClickListener(v -> {
            Intent intent = new Intent(Main.this, Advert.class);
            startActivity(intent);
        });

        list.setOnClickListener(v -> {
            Intent intent = new Intent(Main.this, Home.class);
            startActivity(intent);
        });
    }
}
