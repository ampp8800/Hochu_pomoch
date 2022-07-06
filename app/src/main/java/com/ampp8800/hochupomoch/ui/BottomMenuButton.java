package com.ampp8800.hochupomoch.ui;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;

import com.ampp8800.hochupomoch.R;

import java.io.Serializable;

public enum BottomMenuButton implements Serializable {
    //    NEWS_BUTTON("newsButton", R.color.leaf, R.color.warm_grey, iconSelect, iconUnselect, button, title),
    SEARCH_BUTTON("searchButton",
            R.color.leaf,
            R.color.warm_grey,
            R.drawable.ic_search_green,
            R.drawable.ic_search_grey,
            R.id.iv_search_button,
            R.id.tv_search_button),
    HELP_BUTTON("helpButton",
            R.color.leaf,
            R.color.warm_grey,
            R.drawable.green_circle,
            R.drawable.red_circle,
            R.id.iv_help_button,
            R.id.tv_help_button),
    //    HISTORY_BUTTON("historyButton", R.color.leaf, R.color.warm_grey, iconSelect, iconUnselect, button, title),
    PROFILE_BUTTON("profileButton",
            R.color.leaf,
            R.color.warm_grey,
            R.drawable.ic_profile_green,
            R.drawable.ic_profile_grey,
            R.id.iv_profile_button,
            R.id.tv_profile_button);
    private final String tag;
    private final int colorSelect;
    private final int colorUnselect;
    private final int iconSelect;
    private final int iconUnselect;
    private final int button;
    private final int title;


    BottomMenuButton(String tag,
                     @ColorInt int colorSelect,
                     @ColorInt int colorUnselect,
                     @DrawableRes int iconSelect,
                     @DrawableRes int iconUnselect,
                     @IdRes int button,
                     @IdRes int title) {
        this.tag = tag;
        this.colorSelect = colorSelect;
        this.colorUnselect = colorUnselect;
        this.iconSelect = iconSelect;
        this.iconUnselect = iconUnselect;
        this.button = button;
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public int getColorSelect() {
        return colorSelect;
    }

    public int getColorUnselect() {
        return colorUnselect;
    }

    public int getIconSelect() {
        return iconSelect;
    }

    public int getIconUnselect() {
        return iconUnselect;
    }

    public int getButton() {
        return button;
    }

    public int getTitle() {
        return title;
    }

}
