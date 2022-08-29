package com.ampp8800.hochupomoch.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface NewsInformation {
    @GET("static-response.json")
    Call<List<NewsItem>> getNewsInformation();
}
