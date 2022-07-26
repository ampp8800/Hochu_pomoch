package com.ampp8800.hochupomoch.data;

import androidx.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class NewsItem {
    private final String photoNews;
    private final String newsHeadline;
    private final String briefDescriptionOfNews;
    private String date;

    public NewsItem(@NonNull String photoNews,
                    @NonNull String newsHeadline,
                    @NonNull String briefDescriptionOfNews,
                    @NonNull Long startDate,
                    @NonNull Long endDate) {
        this.photoNews = photoNews;
        this.newsHeadline = newsHeadline;
        this.briefDescriptionOfNews = briefDescriptionOfNews;
        setThisDate(startDate, endDate);
    }

    @NonNull
    public String getPhotoNews() {
        return photoNews;
    }

    @NonNull
    public String getNewsHeadline() {
        return newsHeadline;
    }

    @NonNull
    public String getBriefDescriptionOfNews() {
        return briefDescriptionOfNews;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    private void setThisDate(@NonNull Long startDateMilliseconds, @NonNull Long endDateMilliseconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyLocalizedPattern("dd.MM");
        DateTime currentDate = new DateTime().now();
        if (currentDate.isBefore(startDateMilliseconds)) {
            date = new SimpleDateFormat("LLLL dd, yyyy", new Locale("ru")).format(startDateMilliseconds);
        } else {
            DateTime endDate = new DateTime(endDateMilliseconds);
            date = "Осталось" + Days.daysBetween(currentDate, endDate) +
                    "(" + simpleDateFormat.format(startDateMilliseconds) + " - " +
                    simpleDateFormat.format(endDateMilliseconds) + ")";
        }
    }

}
