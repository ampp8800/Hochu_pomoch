package com.ampp8800.hochupomoch.data;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsInformation;
import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.db.NewsEntity;
import com.ampp8800.hochupomoch.db.NewsEntityDao;
import com.ampp8800.hochupomoch.ui.NewsItemLoadingCallback;
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

    public void loadItemNews(@NonNull NewsItemLoadingCallback newsItemLoadingCallback, @NonNull String guid) {
        NetworkNewsRepository.NewsItemLoaderAsyncTask newsItemLoaderAsyncTask = new NetworkNewsRepository.NewsItemLoaderAsyncTask(newsItemLoadingCallback, guid);
        newsItemLoaderAsyncTask.execute();
    }

    private static class NewsItemsLoaderAsyncTask extends AsyncTask<Void, Void, ArrayList<NewsItemModel>> {
        private final NewsLoadingCallback newsLoadingCallback;

        public NewsItemsLoaderAsyncTask(@NonNull NewsLoadingCallback newsLoadingCallback) {
            this.newsLoadingCallback = newsLoadingCallback;
        }

        @Override
        protected ArrayList<NewsItemModel> doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
            NewsInformation newsInformation = retrofit.create(NewsInformation.class);
            Call<List<NewsItemModel>> messages = newsInformation.getNewsInformation();
            ArrayList<NewsItemModel> news = new ArrayList<>();
            try {
                AppDatabase database = HochuPomochApplication.getInstance().getDatabase();
                NewsEntityDao newsEntityDao = database.newsEntityDao();
                if (newsEntityDao != null) {
                    newsEntityDao.clearAll(newsEntityDao.getAll());
                }
                for (NewsItemModel item : messages.execute().body()) {
                    news.add(item);
                    newsEntityDao.insert(newsItemToNewsEntity(item));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return news;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItemModel> news) {
            super.onPostExecute(news);
            newsLoadingCallback.onNewsUpdate(news);
        }

        @NonNull
        public NewsEntity newsItemToNewsEntity(@NonNull NewsItemModel newsItemModel) {
            NewsEntity newsEntity = new NewsEntity();
            newsEntity.setGuid(newsItemModel.getGuid());
            newsEntity.setName(newsItemModel.getName());
            newsEntity.setFundName(newsItemModel.getFundName());
            newsEntity.setDescription(newsItemModel.getDescription());
            newsEntity.setAddress(newsItemModel.getAddress());
            newsEntity.setStartDate(newsItemModel.getStartDate());
            newsEntity.setEndDate(newsItemModel.getEndDate());
            newsEntity.setPhones(newsItemModel.getPhones().toString());
            newsEntity.setImages(newsItemModel.getImages().toString());
            newsEntity.setEmail(newsItemModel.getEmail());
            newsEntity.setWebsite(newsItemModel.getWebsite());
            return newsEntity;
        }

    }

    private static class NewsItemLoaderAsyncTask extends AsyncTask<Void, Void, NewsItemModel> {
        private final NewsItemLoadingCallback newsItemLoadingCallback;
        private final String guid;

        public NewsItemLoaderAsyncTask(@NonNull NewsItemLoadingCallback newsItemLoadingCallback, @NonNull String guid) {
            this.newsItemLoadingCallback = newsItemLoadingCallback;
            this.guid = guid;
        }

        @Override
        protected NewsItemModel doInBackground(Void... params) {
            AppDatabase database = HochuPomochApplication.getInstance().getDatabase();
            NewsEntityDao newsEntityDao = database.newsEntityDao();
            NewsEntity newsEntity = newsEntityDao.selectNewsEntity(guid);
            if (newsEntity != null){
                return new NewsItemModel(newsEntity);
            }
            return null;
        }

        @Override
        protected void onPostExecute(NewsItemModel newsItemModel) {
            super.onPostExecute(newsItemModel);
            newsItemLoadingCallback.onNewsItemUpdate(newsItemModel);
        }

    }

}
