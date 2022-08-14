package com.ampp8800.hochupomoch.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NewsEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsEntityDao newsEntityDao();
}
