package com.ampp8800.hochupomoch.data;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
import com.ampp8800.hochupomoch.db.NewsEntity;
import com.ampp8800.hochupomoch.db.NewsEntityDao;

import java.util.List;


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

    public void writeToDatabaseListOfNews(@NonNull List<NewsItemModel> newsItemModels) {
        NewsEntityDao newsEntityDao = HochuPomochApplication.getInstance().getDatabase().newsEntityDao();
        if (newsEntityDao != null) {
            newsEntityDao.clearAll(newsEntityDao.getAllAsList());
        }
        for (NewsItemModel item : newsItemModels) {
            newsEntityDao.insert(networkNewsRepository.newsItemToNewsEntity(item));
        }
    }

}
