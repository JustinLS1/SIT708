package com.example.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsAdapter.RelatedNewsViewHolder> {

    private final List<News> relatedNewsList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(News news);
    }

    public static class RelatedNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImage;
        TextView newsTitle;
        TextView newsDescription;

        public RelatedNewsViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.news_image);
            newsTitle = itemView.findViewById(R.id.news_title);
            newsDescription = itemView.findViewById(R.id.news_description);

            itemView.setOnClickListener(v -> {
                News news = (News) v.getTag();
                if (news != null) {
                    listener.onItemClick(news);
                }
            });
        }
    }

    public RelatedNewsAdapter(List<News> relatedNewsList, OnItemClickListener listener) {
        this.relatedNewsList = relatedNewsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RelatedNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_news, parent, false);
        return new RelatedNewsViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedNewsViewHolder holder, int position) {
        News currentItem = relatedNewsList.get(position);
        holder.newsImage.setImageResource(currentItem.getImage());
        holder.newsTitle.setText(currentItem.getTitle());
        holder.newsDescription.setText(currentItem.getDescription());
        holder.itemView.setTag(currentItem);
    }

    @Override
    public int getItemCount() {
        return relatedNewsList.size();
    }
}