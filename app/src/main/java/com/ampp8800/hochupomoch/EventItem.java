package com.ampp8800.hochupomoch;

import androidx.annotation.NonNull;

public class EventItem {
    private String organization;
    private String name;

    public EventItem(String organization, String name) {
        this.organization = organization;
        this.name = name;
    }

    @NonNull
    public String getOrganization() {
        return organization;
    }

    @NonNull
    public String getName() {
        return name;
    }

}
