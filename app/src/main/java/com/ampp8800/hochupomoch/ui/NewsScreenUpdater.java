package com.ampp8800.hochupomoch.ui;

import com.ampp8800.hochupomoch.data.NewsItem;

import java.util.List;

public interface NewsScreenUpdater {
    void newsScreenUpdate(List<NewsItem> newsListItems);

    void hideProgressBar();
}
