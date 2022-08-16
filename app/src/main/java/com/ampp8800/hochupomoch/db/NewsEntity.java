package com.ampp8800.hochupomoch.db;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class NewsEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
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
    private long startDate;
    private long endDate;
    private long phones;
    @NonNull
    private String images;
    @NonNull
    private String email;
    @NonNull
    private String website;

    public long getId() {
        return id;
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
    public long getStartDate() {
        return startDate;
    }
    public long getEndDate() {
        return endDate;
    }
    public long getPhones() {
        return phones;
    }
    @NonNull
    public String getImages() {
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

    public void setId(long id) {
        this.id = id;
    }
    public void setGuid(String guid) {
        this.guid = guid;
    }
    public void setName(@NonNull String name) {
        this.name = name;
    }
    public void setFundName(@NonNull String fundName) {
        this.fundName = fundName;
    }
    public void setDescription(@NonNull String description) {
        this.description = description;
    }
    public void setAddress(@NonNull String address) {
        this.address = address;
    }
    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
    public void setPhones(long phones) {
        this.phones = phones;
    }
    public void setImages(@NonNull String images) {
        this.images = images;
    }
    public void setEmail(@NonNull String email) {
        this.email = email;
    }
    public void setWebsite(@NonNull String website) {
        this.website = website;
    }

}
