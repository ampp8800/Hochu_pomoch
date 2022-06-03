package com.ampp8800.hochupomoch;

import androidx.annotation.NonNull;

public class EventItem {
    private final String organization;
    private final String name;

    public EventItem(@NonNull String organization, @NonNull String name) {
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
