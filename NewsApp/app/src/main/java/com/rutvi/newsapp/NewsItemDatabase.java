package com.rutvi.newsapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {NewsItem.class}, version = 1, exportSchema = false)
public abstract class NewsItemDatabase extends RoomDatabase {
    public abstract NewsItemDao newsItemDao();
    private static volatile NewsItemDatabase INSTANCE;

    static NewsItemDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NewsItemDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NewsItemDatabase.class, "news_item_db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}