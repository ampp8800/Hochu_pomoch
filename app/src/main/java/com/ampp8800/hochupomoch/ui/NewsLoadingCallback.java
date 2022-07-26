package com.ampp8800.hochupomoch.ui;

import androidx.annotation.NonNull;

import java.util.List;

public interface NewsLoadingCallback {
    void onNewsUpdate(@NonNull List newsListItems);
}
