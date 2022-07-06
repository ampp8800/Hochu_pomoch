package com.ampp8800.hochupomoch.ui;

import java.io.Serializable;

public enum BottomMenuButton implements Serializable {
    NEWS_BUTTON("newsButton"),
    SEARCH_BUTTON("searchButton"),
    HELP_BUTTON("helpButton"),
    HISTORY_BUTTON("historyButton"),
    PROFILE_BUTTON("profileButton");
    private String tag;

    BottomMenuButton(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

}
