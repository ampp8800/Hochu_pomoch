package com.ampp8800.hochupomoch.data;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public class NewsItem {
    @DrawableRes
    private final int photoNews;
    private final String newsHeadline;
    private final String briefDescriptionOfNews;
    private final String date;

    public NewsItem(int photoNews, @NonNull String newsHeadline, @NonNull String briefDescriptionOfNews, @NonNull String date) {
        this.photoNews = photoNews;
        this.newsHeadline = newsHeadline;
        this.briefDescriptionOfNews = briefDescriptionOfNews;
        this.date = date;
    }

    public int getPhotoNews() {
        return photoNews;
    }

    public String getNewsHeadline() {
        return newsHeadline;
    }

    public String getBriefDescriptionOfNews() {
        return briefDescriptionOfNews;
    }

    public String getDate() {
        return date;
    }

}
