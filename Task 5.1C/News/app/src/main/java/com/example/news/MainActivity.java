package com.example.news;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TopStoriesAdapter.OnItemClickListener, NewsAdapter.OnItemClickListener {

    private RecyclerView topStoriesRecyclerView;
    private RecyclerView newsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topStoriesRecyclerView = findViewById(R.id.top_recycleView);
        newsRecyclerView = findViewById(R.id.news_recyclerView);

        setupTopStories();

        setupNews();
    }

    private void setupTopStories() {
        List<News> topStoriesList = NewsData.getTopStories();
        TopStoriesAdapter topStoriesAdapter = new TopStoriesAdapter(topStoriesList, this);

        LinearLayoutManager topStoriesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topStoriesRecyclerView.setLayoutManager(topStoriesLayoutManager);
        topStoriesRecyclerView.setAdapter(topStoriesAdapter);
    }

    private void setupNews() {
        List<News> newsList = NewsData.getNews();
        NewsAdapter newsAdapter = new NewsAdapter(newsList, this);

        GridLayoutManager newsLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        newsRecyclerView.setLayoutManager(newsLayoutManager);
        newsRecyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void onItemClick(News news) {
        FrameLayout fragmentContainer = findViewById(R.id.fragment);
        fragmentContainer.setVisibility(View.VISIBLE);

        NewsFragment newsFragment = NewsFragment.newInstance(news);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, newsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}