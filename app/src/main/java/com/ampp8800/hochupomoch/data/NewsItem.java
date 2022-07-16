package com.ampp8800.hochupomoch.data;

import androidx.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;

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
        DateTime startDate = new DateTime(startDateMilliseconds);
        DateTime currentDate = new DateTime().now();
        if (currentDate.isBefore(startDateMilliseconds)) {
            date = getMonth(startDate) + " " + startDate.getDayOfMonth() + ", " + startDate.getYear();
        } else {
            DateTime endDate = new DateTime(endDateMilliseconds);
            date = "Осталось" + Days.daysBetween(currentDate, endDate) +
                    "(" + simpleDateFormat.format(startDate.getMillis()) + " - " + simpleDateFormat.format(endDate.getMillis()) + ")";
        }
    }

    private String getMonth(DateTime dateTime) {
        switch (dateTime.getMonthOfYear()) {
            case 1:
                return "Январь";
            case 2:
                return "Февраль";
            case 3:
                return "Март";
            case 4:
                return "Апрель";
            case 5:
                return "Май";
            case 6:
                return "Июнь";
            case 7:
                return "Июль";
            case 8:
                return "Август";
            case 9:
                return "Сентябрь";
            case 10:
                return "Октябрь";
            case 11:
                return "Ноябрь";
            case 12:
                return "Декабрь";
            default:
                return "Ошибка" + dateTime.getMonthOfYear();
        }
    }

}
