package com.example.news;

import java.util.ArrayList;
import java.util.List;

public class NewsData {

    public static List<News> getTopStories() {
        List<News> topStoriesList = new ArrayList<>();
        topStoriesList.add(new News(R.drawable.zf1_redbull, "Red Bull", "Oracle Red Bull Racing"));
        topStoriesList.add(new News(R.drawable.zf1_ferrari, "Ferrari", "Scuderia Ferrari HP"));
        topStoriesList.add(new News(R.drawable.zf1_mclaren, "McLaren", "McLaren Formula 1 Team"));
        topStoriesList.add(new News(R.drawable.zf1_mercedes, "Mercedes", "Mercedes-AMG PETRONAS Formula One Team"));
        return topStoriesList;
    }

    public static List<News> getNews() {
        List<News> newsList = new ArrayList<>();
        newsList.add(new News(R.drawable.zf1_redbull, "Red Bull", "Oracle Red Bull Racing"));
        newsList.add(new News(R.drawable.zf1_ferrari, "Ferrari", "Scuderia Ferrari HP"));
        newsList.add(new News(R.drawable.zf1_mclaren, "McLaren", "McLaren Formula 1 Team"));
        newsList.add(new News(R.drawable.zf1_mercedes, "Mercedes", "Mercedes-AMG PETRONAS Formula One Team"));
        newsList.add(new News(R.drawable.zf1_williams, "Williams", "Atlassian Williams Racing"));
        newsList.add(new News(R.drawable.zf1_haas, "Haas", "MoneyGram Haas F1 Team"));
        newsList.add(new News(R.drawable.zf1_aston, "Aston Martin", "Aston Martin Aramco Formula One Team"));
        newsList.add(new News(R.drawable.zf1_racingbulls, "Racing Bulls", "Visa Cash App Racing Bulls"));
        newsList.add(new News(R.drawable.zf1_alpine, "Alpine", "BWT Alpine F1 Team"));
        newsList.add(new News(R.drawable.zf1_sauber, "Kick Sauber", "Stake F1 Team Kick Sauber"));
        return newsList;
    }
}
