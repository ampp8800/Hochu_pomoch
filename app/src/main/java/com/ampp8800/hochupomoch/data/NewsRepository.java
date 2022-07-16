package com.ampp8800.hochupomoch.data;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.MessagesApi;
import com.ampp8800.hochupomoch.api.NewsModel;
import com.ampp8800.hochupomoch.ui.OnNewsLoaded;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewsRepository {
    private static NewsRepository newsRepository;
    private final ArrayList<NewsItem> news = new ArrayList<>();

    private NewsRepository() {
    }

    @NonNull
    public static NewsRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    @NonNull
    public ArrayList<NewsItem> getNews() {
        return news;
    }

    public void loadNews(@NonNull OnNewsLoaded onNewsLoaded){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://eithernor.github.io/help-server/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        MessagesApi messagesApi = retrofit.create(MessagesApi.class);

        Call<List<NewsModel>> messages = messagesApi.messages();

        messages.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                news.clear();
                for (NewsModel item : response.body()) {
                    news.add(new NewsItem(item.getImages().get(0),
                            item.getName(),
                            item.getFundName(),
                            item.getStartDate(),
                            item.getEndDate()));
                }
                onNewsLoaded.onNewsLoaded(news);
//                if (response.isSuccessful()) {
//                    System.out.println("response " + response.body());
//                } else {
//                    System.out.println("response code " + response.code());
//                }
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                System.out.println("failure " + t);
            }
        });
    }

}
