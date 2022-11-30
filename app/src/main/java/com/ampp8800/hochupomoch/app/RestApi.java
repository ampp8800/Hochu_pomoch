package com.ampp8800.hochupomoch.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.ampp8800.hochupomoch.api.NewsInformation;
import com.ampp8800.hochupomoch.db.AppDatabase;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi extends Application {

    public static RestApi instance;
    private AppDatabase database;
    private NewsInformation newsInformation;
    @NonNull
    private final static String BASE_URL = "https://eithernor.github.io/help-server/";
    @NonNull
    private final static String NAME_DATABASE = "database";


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, NAME_DATABASE).build();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        newsInformation = retrofit.create(NewsInformation.class);
    }

    public static RestApi getInstance() {
        return instance;
    }

    @NonNull
    public AppDatabase getDatabase(){
        return database;
    }

    public NewsInformation getNewsInformation() {
        return newsInformation;
    }

}
