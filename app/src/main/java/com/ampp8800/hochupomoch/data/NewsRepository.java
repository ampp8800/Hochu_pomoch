package com.ampp8800.hochupomoch.data;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsModel;
import com.ampp8800.hochupomoch.api.NewsInformation;
import com.ampp8800.hochupomoch.ui.NewsLoadingCallback;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewsRepository {
    private static NewsRepository newsRepository;
    @NonNull
    private static final ArrayList<NewsItem> news = new ArrayList<>();

    private NewsRepository() {
    }

    public static NewsRepository newInstance() {
        if (newsRepository == null) {
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    public void executeNewsLoadingAsyncTask(@NonNull NewsLoadingCallback newsLoadingCallback) {
        NewsItemsLoaderAsyncTask newsItemsLoaderAsyncTask = new NewsItemsLoaderAsyncTask(newsLoadingCallback);
        newsItemsLoaderAsyncTask.execute();
    }


    private static class NewsItemsLoaderAsyncTask extends AsyncTask<Void, Void, ArrayList<NewsItem>> {
        private final NewsLoadingCallback newsLoadingCallback;

        public NewsItemsLoaderAsyncTask(@NonNull NewsLoadingCallback newsLoadingCallback) {
            this.newsLoadingCallback = newsLoadingCallback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://eithernor.github.io/help-server/")
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
            NewsInformation newsInformation = retrofit.create(NewsInformation.class);
            Call<List<NewsModel>> messages = newsInformation.getNewsInformation();
            try {
                news.clear();
                for (NewsModel item : messages.execute().body()) {
                    news.add(new NewsItem(item.getImages().get(0),
                            item.getFundName(),
                            item.getDescription(),
                            item.getStartDate(),
                            item.getEndDate()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return news;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> news) {
            super.onPostExecute(news);
            newsLoadingCallback.onNewsUpdate(news);
        }

    }
}
