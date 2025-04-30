package com.example.itube;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private EditText urlInput;
    private TextView resultView;
    private DBHelper db;
    private String username;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        urlInput = findViewById(R.id.urlInput);
        resultView = findViewById(R.id.result);
        Button playButton = findViewById(R.id.playButton);
        Button addButton = findViewById(R.id.addButton);
        Button playlistButton = findViewById(R.id.playlistButton);
        db = new DBHelper(this);
        username = getIntent().getStringExtra("USERNAME");

        playButton.setOnClickListener(view -> {
            String url = urlInput.getText().toString().trim();

            if (url.isEmpty()) {
                resultView.setText("Please enter a YouTube URL");
            } else {
                Intent intent = new Intent(Home.this, Video.class);
                intent.putExtra("URL", url);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(view -> addURL());

        playlistButton.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, Playlist.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        });
    }

    @SuppressLint("SetTextI18n")
    private void addURL() {
        String url = urlInput.getText().toString().trim();

        if (url.isEmpty()) {
            resultView.setText("Please enter a YouTube URL");
            return;
        }

        boolean added = db.addURL(username, url);
        if (added) {
            resultView.setText("YouTube URL added successfully");
            setResult(RESULT_OK);
        } else {
            resultView.setText("YouTube URL may exist already");
        }
    }
}