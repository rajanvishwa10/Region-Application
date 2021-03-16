package com.android.regionapplication.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {RegionModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RegionDao regionDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "Region_DB").allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
