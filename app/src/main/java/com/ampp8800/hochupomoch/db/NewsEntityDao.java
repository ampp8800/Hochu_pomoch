package com.ampp8800.hochupomoch.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsEntityDao {

    @Query("SELECT * FROM newsEntity")
    List<NewsEntity> getAll();

    @Delete
    void clearAll(List<NewsEntity> newsEntities);

    @Insert
    void insert(NewsEntity newsEntity);
}
