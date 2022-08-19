package com.ampp8800.hochupomoch.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.ampp8800.hochupomoch.db.AppDatabase;

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

    @NonNull
    public AppDatabase getDatabase(){
        return database;
    }
}
