package com.example.itube;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceError;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Video extends AppCompatActivity {

    private WebView yt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);

        yt = findViewById(R.id.video);
        setupWebView();

        String videoUrl = getIntent().getStringExtra("URL");
        if (videoUrl != null) {
            String videoId = extractVideoId(videoUrl);
            if (videoId != null) {
                String embedUrl = "https://www.youtube.com/embed/" + videoId + "?enablejsapi=1";
                yt.loadUrl(embedUrl);
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        yt.getSettings().setJavaScriptEnabled(true);
        yt.getSettings().setDomStorageEnabled(true);
        yt.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        yt.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        yt.setWebChromeClient(new WebChromeClient());
    }

    private String extractVideoId(String url) {
        String videoId = null;
        Pattern pattern = Pattern.compile("(?<=v=|/)([a-zA-Z0-9_-]{11})");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            videoId = matcher.group(0);
        }
        return videoId;
    }

    @Override
    protected void onPause() {
        super.onPause();
        yt.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        yt.onResume();
    }

    @Override
    protected void onDestroy() {
        yt.destroy();
        super.onDestroy();
    }
}