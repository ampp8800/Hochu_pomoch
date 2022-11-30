package com.ampp8800.hochupomoch.data;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.RestApi;
import com.ampp8800.hochupomoch.db.NewsEntity;
import com.ampp8800.hochupomoch.db.NewsEntityDao;

import java.util.ArrayList;
import java.util.List;

public class DatabaseNewsRepository {
    private static DatabaseNewsRepository databaseNewsRepository;
    private NewsEntityDao newsEntityDao;

    private DatabaseNewsRepository() {
    }

    public static DatabaseNewsRepository newInstance() {
        if (databaseNewsRepository == null) {
            databaseNewsRepository = new DatabaseNewsRepository();
        }
        return databaseNewsRepository;
    }

    public void writeToDatabaseListOfNews(@NonNull List<NewsItemModel> newsItemModels) {
        newsEntityDao = RestApi.getInstance().getDatabase().newsEntityDao();
        if (newsEntityDao != null) {
            newsEntityDao.clearAll();
        }
        List<NewsEntity> newsEntities = new ArrayList<>();
        for (NewsItemModel item : newsItemModels) {
            newsEntities.add(newsItemToNewsEntity(item));
        }
        newsEntityDao.insert(newsEntities);
    }

    @NonNull
    public static NewsEntity newsItemToNewsEntity(@NonNull NewsItemModel newsItemModel) {
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
