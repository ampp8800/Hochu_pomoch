package com.ampp8800.hochupomoch.api;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.db.NewsEntity;
import com.ampp8800.hochupomoch.ui.NewsDetailsDataConverter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsItemModel {

    @NonNull
    @SerializedName("guid")
    private String guid;
    @NonNull
    @SerializedName("name")
    private String name;
    @NonNull
    @SerializedName("fundName")
    private String fundName;
    @NonNull
    @SerializedName("description")
    private String description;
    @NonNull
    @SerializedName("address")
    private String address;
    @NonNull
    @SerializedName("startDate")
    private Long startDate;
    @NonNull
    @SerializedName("endDate")
    private Long endDate;
    @NonNull
    @SerializedName("phones")
    private List<Long> phones;
    @NonNull
    @SerializedName("images")
    private List<String> images;
    @NonNull
    @SerializedName("email")
    private String email;
    @NonNull
    @SerializedName("website")
    private String website;


    public NewsItemModel(@NonNull NewsEntity newsEntity){
        this.guid = newsEntity.getGuid();
        this.name =newsEntity.getName();
        this.fundName =newsEntity.getFundName();
        this.description = newsEntity.getDescription();
        this.address = newsEntity.getAddress();
        this.startDate = newsEntity.getStartDate();
        this.endDate = newsEntity.getEndDate();
        this.phones = NewsDetailsDataConverter.convertStringToListOfLongs(newsEntity.getPhones());
        this.images = NewsDetailsDataConverter.convertStringToListOfStrings(newsEntity.getImages());
        this.email = newsEntity.getEmail();
        this.website = newsEntity.getWebsite();
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

}
