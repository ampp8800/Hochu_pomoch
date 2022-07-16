package com.ampp8800.hochupomoch.ui;

import com.ampp8800.hochupomoch.data.NewsItem;

import java.util.List;

public interface OnNewsLoaded {
    void onNewsLoaded(List<NewsItem> newsListItems);
}
