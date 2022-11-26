package com.ampp8800.hochupomoch.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface NewsEntityDao {

    @Query("SELECT * FROM newsEntity")
    Single<List<NewsEntity>> getAll();

    @Query("SELECT * FROM newsEntity WHERE guid = :currentGuid")
    Single<NewsEntity> selectNewsEntity(String currentGuid);

    @Query("DELETE FROM newsentity")
    void clearAll();

    @Insert
    void insert(List<NewsEntity> newsEntities);
}
