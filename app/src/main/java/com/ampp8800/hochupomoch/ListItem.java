package com.ampp8800.hochupomoch;

public class ListItem {
    private final String name;
    private int imageHelpResource;
    private String imageViewURL;
    private String dateOfBirth;
    private String fieldOfActivity;


    public ListItem(String name, int imageResource) {
        this.name = name;
        this.imageHelpResource = imageResource;
    }

    public ListItem(String name, String imageViewURL) {
        this.name = name;
        this.imageViewURL = imageViewURL;
    }

    public ListItem(String name, String imageViewURL, String dateOfBirth, String fieldOfActivity) {
        this.name = name;
        this.imageViewURL = imageViewURL;
        this.dateOfBirth = dateOfBirth;
        this.fieldOfActivity = fieldOfActivity;
    }

    public String getName() {
        return this.name;
    }

    public int getImageResource() {
        return this.imageHelpResource;
    }

    public String getImageViewURL() {
        return imageViewURL;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFieldOfActivity() {
        return fieldOfActivity;
    }
}
