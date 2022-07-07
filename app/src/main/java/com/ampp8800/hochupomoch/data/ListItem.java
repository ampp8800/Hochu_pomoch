package com.ampp8800.hochupomoch.data;

import androidx.annotation.NonNull;

public class ListItem {
    private final String name;
    private int imageHelpResource;
    private String imageViewURL;
    private String dateOfBirth;
    private String fieldOfActivity;


    public ListItem(@NonNull String name, int imageResource) {
        this.name = name;
        this.imageHelpResource = imageResource;
    }

    public ListItem(@NonNull String name, @NonNull String imageViewURL) {
        this.name = name;
        this.imageViewURL = imageViewURL;
    }

    public ListItem(@NonNull String name, String imageViewURL, String dateOfBirth, String fieldOfActivity) {
        this.name = name;
        this.imageViewURL = imageViewURL;
        this.dateOfBirth = dateOfBirth;
        this.fieldOfActivity = fieldOfActivity;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getImageResource() {
        return this.imageHelpResource;
    }

    @NonNull
    public String getImageViewURL() {
        return imageViewURL;
    }

    @NonNull
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @NonNull
    public String getFieldOfActivity() {
        return fieldOfActivity;
    }

}