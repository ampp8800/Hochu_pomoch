package com.ampp8800.hochupomoch.data;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.ui.HochuPomochApplication;
import com.ampp8800.hochupomoch.db.NewsEntity;
import com.ampp8800.hochupomoch.db.NewsEntityDao;
import com.ampp8800.hochupomoch.ui.NewsLoadingCallback;

import java.util.ArrayList;
import java.util.List;

public class InternalNewsRepository {
    private static InternalNewsRepository internalNewsRepository;
    @NonNull
    private static final ArrayList<NewsItem> news = new ArrayList<>();

    private InternalNewsRepository() {
    }

    public static InternalNewsRepository newInstance() {
        if (internalNewsRepository == null) {
            internalNewsRepository = new InternalNewsRepository();
        }
        return internalNewsRepository;
    }

    public void executeNewsLoadingAsyncTask(@NonNull NewsLoadingCallback newsLoadingCallback) {
        InternalNewsRepository.NewsItemsLoaderAsyncTask newsItemsLoaderAsyncTask = new InternalNewsRepository.NewsItemsLoaderAsyncTask(newsLoadingCallback);
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
            news.clear();
            AppDatabase database = HochuPomochApplication.getInstance().getDatabase();
            NewsEntityDao newsEntityDao = database.newsEntityDao();
            List<NewsEntity> newsEntities = newsEntityDao.getAll();
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
