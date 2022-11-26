package com.ampp8800.hochupomoch.api;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;


public interface NewsInformation {
    @GET("static-response.json")
    Single<List<NewsItemModel>> getNewsInformation();
}
