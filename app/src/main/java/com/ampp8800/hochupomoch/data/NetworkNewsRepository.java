package com.ampp8800.hochupomoch.data;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsInformation;
import com.ampp8800.hochupomoch.api.NewsItem;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.db.NewsEntity;
import com.ampp8800.hochupomoch.db.NewsEntityDao;
import com.ampp8800.hochupomoch.ui.NewsLoadingCallback;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkNewsRepository {
    private static NetworkNewsRepository networkNewsRepository;
    private final static String BASE_URL = "https://eithernor.github.io/help-server/";

    private NetworkNewsRepository() {
    }

    public static NetworkNewsRepository newInstance() {
        if (networkNewsRepository == null) {
            networkNewsRepository = new NetworkNewsRepository();
        }
        return networkNewsRepository;
    }

    public void loadNews(@NonNull NewsLoadingCallback newsLoadingCallback) {
        NewsItemsLoaderAsyncTask newsItemsLoaderAsyncTask = new NewsItemsLoaderAsyncTask(newsLoadingCallback);
        newsItemsLoaderAsyncTask.execute();
    }


    private static class NewsItemsLoaderAsyncTask extends AsyncTask<Void, Void, ArrayList<NewsItem>> {
        private final NewsLoadingCallback newsLoadingCallback;

        public NewsItemsLoaderAsyncTask(@NonNull NewsLoadingCallback newsLoadingCallback) {
            this.newsLoadingCallback = newsLoadingCallback;
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
            NewsInformation newsInformation = retrofit.create(NewsInformation.class);
            Call<List<NewsItem>> messages = newsInformation.getNewsInformation();
            ArrayList<NewsItem> news = new ArrayList<>();
            try {
                AppDatabase database = HochuPomochApplication.getInstance().getDatabase();
                NewsEntityDao newsEntityDao = database.newsEntityDao();
                if (newsEntityDao != null) {
                    newsEntityDao.clearAll(newsEntityDao.getAll());
                }
                for (NewsItem item : messages.execute().body()) {
                    news.add(item);
                    newsEntityDao.insert(newsItemToNewsEntity(item.getGuid(),
                            item.getName(),
                            item.getFundName(),
                            item.getDescription(),
                            item.getAddress(),
                            item.getStartDate(),
                            item.getEndDate(),
                            item.getPhones().toString(),
                            item.getImages().toString(),
                            item.getEmail(),
                            item.getWebsite()));
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

        @NonNull
        public NewsEntity newsItemToNewsEntity(@NonNull String guid,
                                         @NonNull String name,
                                         @NonNull String fundName,
                                         @NonNull String description,
                                         @NonNull String address,
                                         long startDate,
                                         long endDate,
                                         @NonNull String phones,
                                         @NonNull String image,
                                         @NonNull String email,
                                         @NonNull String website) {
            NewsEntity newsEntity = new NewsEntity();
            newsEntity.setGuid(guid);
            newsEntity.setName(name);
            newsEntity.setFundName(fundName);
            newsEntity.setDescription(description);
            newsEntity.setAddress(address);
            newsEntity.setStartDate(startDate);
            newsEntity.setEndDate(endDate);
            newsEntity.setPhones(phones);
            newsEntity.setImages(image);
            newsEntity.setEmail(email);
            newsEntity.setWebsite(website);
            return newsEntity;
        }

    }

}
