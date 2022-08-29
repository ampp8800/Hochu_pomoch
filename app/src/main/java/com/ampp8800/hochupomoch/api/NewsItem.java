package com.ampp8800.hochupomoch.api;

import android.icu.text.SimpleDateFormat;

import androidx.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class NewsItem {

    @NonNull
    private String guid;
    @NonNull
    private String name;
    @NonNull
    private String fundName;
    @NonNull
    private String description;
    @NonNull
    private String address;
    @NonNull
    private Long startDate;
    @NonNull
    private Long endDate;
    @NonNull
    private List<Long> phones;
    @NonNull
    private List<String> images;
    @NonNull
    private String email;
    @NonNull
    private String website;


    public NewsItem(@NonNull String guid,
                    @NonNull String name,
                    @NonNull String fundName,
                    @NonNull String description,
                    @NonNull String address,
                    @NonNull Long startDate,
                    @NonNull Long endDate,
                    @NonNull String phones,
                    @NonNull String images,
                    @NonNull String email,
                    @NonNull String website) {
        this.guid = guid;
        this.name = name;
        this.fundName = fundName;
        this.description = description;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.phones = convertStringToArrayLongs(phones);
        this.images = convertStringToArrayStrings(images);
        this.email = email;
        this.website = website;
    }

    @NonNull
    public String getGuid() {
        return guid;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getFundName() {
        return fundName;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    @NonNull
    public Long getStartDate() {
        return startDate;
    }

    @NonNull
    public Long getEndDate() {
        return endDate;
    }

    @NonNull
    public List<Long> getPhones() {
        return phones;
    }

    @NonNull
    public List<String> getImages() {
        return images;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getWebsite() {
        return website;
    }

    @NonNull
    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyLocalizedPattern("dd.MM");
        DateTime currentDate = new DateTime().now();
        if (currentDate.isBefore(startDate)) {
            return new SimpleDateFormat("LLLL dd, yyyy", new Locale("ru")).format(startDate);
        } else {
            DateTime endDateTime = new DateTime(endDate);
            return "Осталось" + Days.daysBetween(currentDate, endDateTime) +
                    "(" + simpleDateFormat.format(startDate) + " - " +
                    simpleDateFormat.format(endDate) + ")";
        }
    }

    private List<Long> convertStringToArrayLongs(@NonNull String string) {
        List<String> stringsArray = Arrays.asList(string.replaceAll("[^\\dA-Za-z ]", "").split(" "));
        List<Long> result = new ArrayList<>();
        for (String number : stringsArray) {
                result.add(Long.parseLong(number));
            }
        return result;
    }

    private List<String> convertStringToArrayStrings(@NonNull String string) {
        return Arrays.asList(string.split("\\s*,\\s*"));
    }

}
