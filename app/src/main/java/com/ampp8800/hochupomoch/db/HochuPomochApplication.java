package com.ampp8800.hochupomoch.db;

import android.app.Application;

import androidx.room.Room;

public class HochuPomochApplication extends Application {

    public static HochuPomochApplication instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database").build();
    }

    public static HochuPomochApplication getInstance() {
        return instance;
    }

    public AppDatabase getDatabase(){
        return database;
    }
}
