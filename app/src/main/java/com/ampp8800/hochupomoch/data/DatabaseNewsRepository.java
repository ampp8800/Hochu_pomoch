package com.ampp8800.hochupomoch.data;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.db.NewsEntity;
import com.ampp8800.hochupomoch.db.NewsEntityDao;
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

    public void newsLoading(@NonNull NewsLoadingCallback newsLoadingCallback) {
        DatabaseNewsRepository.NewsItemsLoaderAsyncTask newsItemsLoaderAsyncTask = new DatabaseNewsRepository.NewsItemsLoaderAsyncTask(newsLoadingCallback);
        newsItemsLoaderAsyncTask.execute();
    }


    private static class NewsItemsLoaderAsyncTask extends AsyncTask<Void, Void, ArrayList<NewsItem>> {
        private final NewsLoadingCallback newsLoadingCallback;

        public NewsItemsLoaderAsyncTask(@NonNull NewsLoadingCallback newsLoadingCallback) {
            this.newsLoadingCallback = newsLoadingCallback;
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(Void... params) {
            AppDatabase database = HochuPomochApplication.getInstance().getDatabase();
            NewsEntityDao newsEntityDao = database.newsEntityDao();
            List<NewsEntity> newsEntities = newsEntityDao.getAll();
            ArrayList<NewsItem> news = new ArrayList<>();
            for (NewsEntity newsEntity : newsEntities) {
                news.add(new NewsItem(newsEntity.getImages(),
                        newsEntity.getFundName(),
                        newsEntity.getDescription(),
                        newsEntity.getStartDate(),
                        newsEntity.getEndDate()));
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
