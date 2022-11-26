package com.ampp8800.hochupomoch.data;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.api.NewsItemModel;
import com.ampp8800.hochupomoch.app.HochuPomochApplication;
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
        newsEntityDao = HochuPomochApplication.getInstance().getDatabase().newsEntityDao();
        NetworkNewsRepository networkNewsRepository = NetworkNewsRepository.newInstance();
        if (newsEntityDao != null) {
            newsEntityDao.clearAll();
        }
        List<NewsEntity> newsEntities = new ArrayList<>();
        for (NewsItemModel item : newsItemModels) {
            newsEntities.add(networkNewsRepository.newsItemToNewsEntity(item));
        }
        newsEntityDao.insert(newsEntities);
    }

}
