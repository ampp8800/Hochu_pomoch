package com.ampp8800.hochupomoch.api;

import androidx.annotation.NonNull;

import java.util.List;

public class NewsModel {

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

}
