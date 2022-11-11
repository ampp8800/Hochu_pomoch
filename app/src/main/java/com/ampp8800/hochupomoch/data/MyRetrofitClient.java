package com.ampp8800.hochupomoch.data;

import com.ampp8800.hochupomoch.api.NewsInformation;
import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofitClient {
    private final static String BASE_URL = "https://eithernor.github.io/help-server/";
    private static MyRetrofitClient myRetrofitClient;
    private NewsInformation newsInformation;

    private MyRetrofitClient() {
        final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        newsInformation = retrofit.create(NewsInformation.class);
    }

    public static MyRetrofitClient getInstance() {
        if (myRetrofitClient == null) {
            myRetrofitClient = new MyRetrofitClient();
        }
        return myRetrofitClient;
    }

    public io.reactivex.Observable<List<NewsItemModel>> getStarredRepose() {
        return newsInformation.getNewsInformation();
    }
}
