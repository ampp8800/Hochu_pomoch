package com.ampp8800.hochupomoch.data;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.db.AppDatabase;
import com.ampp8800.hochupomoch.db.NewsEntity;
import com.ampp8800.hochupomoch.db.NewsEntityDao;
import com.ampp8800.hochupomoch.ui.NewsItemLoadingCallbackOnline;
import com.ampp8800.hochupomoch.ui.NewsItemModelAndConnect;


public class NetworkNewsRepository {
    private static NetworkNewsRepository networkNewsRepository;

    private NetworkNewsRepository() {
    }

    public static NetworkNewsRepository newInstance() {
        if (networkNewsRepository == null) {
            networkNewsRepository = new NetworkNewsRepository();
        }
        return networkNewsRepository;
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

    public void loadItemNews(@NonNull NewsItemLoadingCallbackOnline newsItemLoadingCallbackOnline, @NonNull String guid) {
        NetworkNewsRepository.NewsItemLoaderAsyncTask newsItemLoaderAsyncTask = new NetworkNewsRepository.NewsItemLoaderAsyncTask(newsItemLoadingCallbackOnline, guid);
        newsItemLoaderAsyncTask.execute();
    }

    private static class NewsItemLoaderAsyncTask extends AsyncTask<Void, Void, NewsItemModelAndConnect> {
        private final NewsItemLoadingCallbackOnline newsItemLoadingCallbackOnline;
        private final String guid;
        private boolean isExeption;

        public NewsItemLoaderAsyncTask(@NonNull NewsItemLoadingCallbackOnline newsItemLoadingCallbackOnline, @NonNull String guid) {
            this.newsItemLoadingCallbackOnline = newsItemLoadingCallbackOnline;
            this.guid = guid;
        }

        @Override
        protected NewsItemModelAndConnect doInBackground(Void... params) {
            NewsItemModel newsItemModel = null;
            try {
                AppDatabase database = HochuPomochApplication.getInstance().getDatabase();
                NewsEntityDao newsEntityDao = database.newsEntityDao();
                NewsEntity newsEntity = newsEntityDao.selectNewsEntity(guid);
                newsItemModel = new NewsItemModel(newsEntity);
            } catch (Exception e) {
                e.printStackTrace();
                isExeption = true;
            }
            return new NewsItemModelAndConnect(newsItemModel, isExeption);
        }

        @Override
        protected void onPostExecute(NewsItemModelAndConnect newsItemModelAndConnect) {
            super.onPostExecute(newsItemModelAndConnect);
            newsItemLoadingCallbackOnline.onNewsItemUpdate(newsItemModelAndConnect);
        }

    }

}
