package com.ampp8800.hochupomoch.ui;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;

import com.ampp8800.hochupomoch.R;

import java.io.Serializable;

public enum BottomMenuButton implements Serializable {
    NEWS_BUTTON("newsButton",
            R.color.leaf,
            R.color.warm_grey,
            R.drawable.ic_news_green,
            R.drawable.ic_news_grey,
            R.id.iv_news_button,
            R.id.tv_news_button),
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
    PROFILE_BUTTON("profileButton",
            R.color.leaf,
            R.color.warm_grey,
            R.drawable.ic_profile_green,
            R.drawable.ic_profile_grey,
            R.id.iv_profile_button,
            R.id.tv_profile_button);
    private final String tag;
    @ColorInt
    private final int textColorSelected;
    @ColorInt
    private final int textColorUnselected;
    @DrawableRes
    private final int iconSelected;
    @DrawableRes
    private final int iconUnselected;
    @IdRes
    private final int button;
    @IdRes
    private final int title;


    BottomMenuButton(String tag,
                     @ColorInt int textColorSelected,
                     @ColorInt int textColorUnselected,
                     @DrawableRes int iconSelected,
                     @DrawableRes int iconUnselected,
                     @IdRes int button,
                     @IdRes int title) {
        this.tag = tag;
        this.textColorSelected = textColorSelected;
        this.textColorUnselected = textColorUnselected;
        this.iconSelected = iconSelected;
        this.iconUnselected = iconUnselected;
        this.button = button;
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    @ColorInt
    public int getTextColorSelected() {
        return textColorSelected;
    }

    @ColorInt
    public int getTextColorUnselected() {
        return textColorUnselected;
    }

    @DrawableRes
    public int getIconSelected() {
        return iconSelected;
    }

    @DrawableRes
    public int getIconUnselected() {
        return iconUnselected;
    }

    @IdRes
    public int getButton() {
        return button;
    }

    @IdRes
    public int getTitle() {
        return title;
    }

}
