package com.ampp8800.hochupomoch;

public class HelpListItem {
    private final String name;
    private final int imageHelpResource;


    public HelpListItem(String name, int imageHelpResource) {

        this.name = name;
        this.imageHelpResource = imageHelpResource;
    }

    public String getName() {
        return this.name;
    }

    public int getImageHelpResource() {
        return this.imageHelpResource;
    }

}
