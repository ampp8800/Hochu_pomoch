package com.ampp8800.hochupomoch.data;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.db.NewsEntity;
import com.ampp8800.hochupomoch.db.NewsEntityDao;
import com.ampp8800.hochupomoch.ui.NewsItemLoadingCallbackOffline;
import com.ampp8800.hochupomoch.ui.NewsLoadingCallback;

import java.util.ArrayList;
import java.util.List;

public class DatabaseNewsRepository {
    private static DatabaseNewsRepository databaseNewsRepository;

    private DatabaseNewsRepository() {
    }

    public static DatabaseNewsRepository newInstance() {
        if (databaseNewsRepository == null) {
            databaseNewsRepository = new DatabaseNewsRepository();
        }
        return databaseNewsRepository;
    }

    public void loadNews(@NonNull NewsLoadingCallback newsLoadingCallback) {
        DatabaseNewsRepository.NewsItemsLoaderAsyncTask newsItemsLoaderAsyncTask = new DatabaseNewsRepository.NewsItemsLoaderAsyncTask(newsLoadingCallback);
        newsItemsLoaderAsyncTask.execute();
    }

    public void loadItemNews(@NonNull NewsItemLoadingCallbackOffline newsItemLoadingCallbackOffline, @NonNull String guid) {
        DatabaseNewsRepository.NewsItemLoaderAsyncTask newsItemLoaderAsyncTask = new DatabaseNewsRepository.NewsItemLoaderAsyncTask(newsItemLoadingCallbackOffline, guid);
        newsItemLoaderAsyncTask.execute();
    }


    private static class NewsItemsLoaderAsyncTask extends AsyncTask<Void, Void, ArrayList<NewsItemModel>> {
        private final NewsLoadingCallback newsLoadingCallback;

        public NewsItemsLoaderAsyncTask(@NonNull NewsLoadingCallback newsLoadingCallback) {
            this.newsLoadingCallback = newsLoadingCallback;
        }

        @Override
        protected ArrayList<NewsItemModel> doInBackground(Void... params) {
            AppDatabase database = HochuPomochApplication.getInstance().getDatabase();
            NewsEntityDao newsEntityDao = database.newsEntityDao();
            List<NewsEntity> newsEntities = newsEntityDao.getAll();
            ArrayList<NewsItemModel> news = new ArrayList<>();
            for (NewsEntity newsEntity : newsEntities) {
                news.add(new NewsItemModel(newsEntity));
            }
            return news;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItemModel> news) {
            super.onPostExecute(news);
            newsLoadingCallback.onNewsUpdate(news);
        }

    }

    private static class NewsItemLoaderAsyncTask extends AsyncTask<Void, Void, NewsItemModel> {
        private final NewsItemLoadingCallbackOffline newsItemLoadingCallbackOffline;
        private final String guid;

        public NewsItemLoaderAsyncTask(@NonNull NewsItemLoadingCallbackOffline newsItemLoadingCallbackOffline, @NonNull String guid) {
            this.newsItemLoadingCallbackOffline = newsItemLoadingCallbackOffline;
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
            newsItemLoadingCallbackOffline.onNewsItemUpdate(newsItemModel);
        }

    }
}
