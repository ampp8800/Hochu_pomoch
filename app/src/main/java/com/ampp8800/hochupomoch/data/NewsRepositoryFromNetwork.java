package com.ampp8800.hochupomoch.data;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsInformation;
import com.ampp8800.hochupomoch.api.NewsModel;
import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.ui.HochuPomochApplication;
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


public class NewsRepositoryFromNetwork {
    private static NewsRepositoryFromNetwork newsRepositoryFromNetwork;
    private static boolean isConnect;
    @NonNull
    private static final ArrayList<NewsItem> news = new ArrayList<>();

    private NewsRepositoryFromNetwork() {
    }

    public static NewsRepositoryFromNetwork newInstance() {
        if (newsRepositoryFromNetwork == null) {
            newsRepositoryFromNetwork = new NewsRepositoryFromNetwork();
        }
        return newsRepositoryFromNetwork;
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

                AppDatabase database = HochuPomochApplication.getInstance().getDatabase();
                NewsEntityDao newsEntityDao = database.newsEntityDao();
                if (newsEntityDao != null) {
                    newsEntityDao.clearAll(newsEntityDao.getAll());
                }
                for (NewsModel item : messages.execute().body()) {
                    news.add(new NewsItem(item.getImages().get(0),
                            item.getFundName(),
                            item.getDescription(),
                            item.getStartDate(),
                            item.getEndDate()));

                    newsItemToNewsEntity(newsEntityDao,
                            item.getGuid(),
                            item.getName(),
                            item.getFundName(),
                            item.getDescription(),
                            item.getAddress(),
                            item.getStartDate(),
                            item.getEndDate(),
                            item.getPhones().get(0),
                            item.getImages().get(0),
                            item.getEmail(),
                            item.getWebsite());
                }
            } catch (
                    IOException e) {
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
        public void newsItemToNewsEntity(@NonNull NewsEntityDao newsEntityDao,
                                         @NonNull String guid,
                                         @NonNull String name,
                                         @NonNull String fundName,
                                         @NonNull String description,
                                         @NonNull String address,
                                         long startDate,
                                         long endDate,
                                         long phones,
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
            newsEntityDao.insert(newsEntity);
        }

    }

}
