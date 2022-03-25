package com.ampp8800.hochupomoch;

public class Help {
    private String name; // название
    private int imageHelpResource; // ресурс флага

    public Help(String name, int imageHelpResource){

        this.name = name;
        this.imageHelpResource = imageHelpResource;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageHelpResource() {
        return this.imageHelpResource;
    }

    public void setImageHelpResource(int imageHelpResource) {
        this.imageHelpResource = imageHelpResource;
    }
}
