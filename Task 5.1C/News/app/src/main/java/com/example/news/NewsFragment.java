package com.example.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsFragment extends Fragment implements RelatedNewsAdapter.OnItemClickListener {

    private static final String ARG_NEWS_ITEM = "news_item";

    public static NewsFragment newInstance(News newsItem) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NEWS_ITEM, newsItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ImageView newsImage = view.findViewById(R.id.news_image);
        TextView newsDescription = view.findViewById(R.id.news_description);
        TextView newsTitle = view.findViewById(R.id.news_title);
        RecyclerView relatedNewsRecyclerView = view.findViewById(R.id.related_recyclerView);
        ImageView backButton = view.findViewById(R.id.back_button);

        News newsItem = (News) requireArguments().getSerializable(ARG_NEWS_ITEM);
        if (newsItem != null) {
            newsImage.setImageResource(newsItem.getImage());
            newsDescription.setText(newsItem.getDescription());
            newsTitle.setText(newsItem.getTitle());
        }

        List<News> relatedNews = NewsData.getNews();
        relatedNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        relatedNewsRecyclerView.setAdapter(new RelatedNewsAdapter(relatedNews, this));

        backButton.setOnClickListener(v -> {
            FrameLayout fragmentContainer = requireActivity().findViewById(R.id.fragment);
            fragmentContainer.setVisibility(View.GONE);
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return view;
    }

    @Override
    public void onItemClick(News news) {
        NewsFragment newsFragment = NewsFragment.newInstance(news);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, newsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}