package com.example.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesAdapter.TopStoriesViewHolder> {

    private final List<News> topStoriesList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(News news);
    }

    public static class TopStoriesViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public TopStoriesViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.topNews_image);
            itemView.setOnClickListener(v -> {
                News news = (News) v.getTag();
                if (news != null) {
                    listener.onItemClick(news);
                }
            });
        }
    }

    public TopStoriesAdapter(List<News> topStoriesList, OnItemClickListener listener) {
        this.topStoriesList = topStoriesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopStoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_stories, parent, false);
        return new TopStoriesViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TopStoriesViewHolder holder, int position) {
        News currentItem = topStoriesList.get(position);
        holder.image.setImageResource(currentItem.getImage());
        holder.itemView.setTag(currentItem);
    }

    @Override
    public int getItemCount() {
        return topStoriesList.size();
    }
}