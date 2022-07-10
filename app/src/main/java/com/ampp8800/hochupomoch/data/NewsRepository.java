package com.ampp8800.hochupomoch.data;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.R;

import java.util.ArrayList;

public class NewsRepository {
    private static NewsRepository newsRepository;
    private final ArrayList<NewsItem> news = new ArrayList<>();

    private NewsRepository() {
        addNewsItem(R.drawable.children, "Заголовок новости", "Краткое описание", "Дата проведения");
        addNewsItem(R.drawable.children, "Заголовок новости", "Краткое описание", "Дата проведения");
        addNewsItem(R.drawable.children, "Заголовок новости", "Краткое описание", "Дата проведения");
        addNewsItem(R.drawable.children, "Заголовок новости", "Краткое описание", "Дата проведения");
        addNewsItem(R.drawable.children, "Заголовок новости", "Краткое описание", "Дата проведения");
        addNewsItem(R.drawable.children, "Заголовок новости", "Краткое описание", "Дата проведения");
    }

    @NonNull
    public static NewsRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    @NonNull
    public ArrayList<NewsItem> getNews() {
        return news;
    }

    private void addNewsItem(int photoNews, @NonNull String newsHeadline, @NonNull String briefDescriptionOfNews, @NonNull String date) {
        news.add(new NewsItem(photoNews, newsHeadline, briefDescriptionOfNews, date));
    }

}
