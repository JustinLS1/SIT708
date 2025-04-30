package com.example.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final List<News> newsList;
    private final OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(News news);
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView description;

        public NewsViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.news_image);
            title = itemView.findViewById(R.id.news_title);
            description = itemView.findViewById(R.id.news_description);

            itemView.setOnClickListener(v -> {
                News news = (News) v.getTag();
                if (news != null) {
                    listener.onItemClick(news);
                }
            });
        }
    }

    public NewsAdapter(List<News> newsList, OnItemClickListener listener) {
        this.newsList = newsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news, parent, false);
        return new NewsViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News currentItem = newsList.get(position);
        holder.image.setImageResource(currentItem.getImage());
        holder.title.setText(currentItem.getTitle());
        holder.description.setText(currentItem.getDescription());
        holder.itemView.setTag(currentItem);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}