package com.ampp8800.hochupomoch;

public class ListItem {
    private final String name;
    private final int imageHelpResource;


    public ListItem(String name, int imageResource) {

        this.name = name;
        this.imageHelpResource = imageResource;
    }

    public String getName() {
        return this.name;
    }

    public int getImageResource() {
        return this.imageHelpResource;
    }

}
