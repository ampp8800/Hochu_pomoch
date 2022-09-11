package com.ampp8800.hochupomoch.ui;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;

public interface NewsItemLoadingCallback {
    void onNewsItemUpdate(@NonNull NewsItemModel newsItemModel);
}
