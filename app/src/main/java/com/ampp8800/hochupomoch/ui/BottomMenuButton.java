package com.ampp8800.hochupomoch.ui;

import java.io.Serializable;

public enum BottomMenuButton implements Serializable {
    NEWS_BUTTON("newsButton", false),
    SEARCH_BUTTON("searchButton", false),
    HELP_BUTTON("helpButton", false),
    HISTORY_BUTTON("historyButton", false),
    PROFILE_BUTTON("profileButton", false);
    private String tag;
    private boolean isSelect;

    BottomMenuButton(String tag, boolean isSelect) {
    }

    public String getTag() {
        return tag;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
