package com.vivek.omdb.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.vivek.omdb.model.Movie;


@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase ourInstance ;
    public abstract BookmarkDao bookmarkDao();

    public static AppDatabase getAppDatabase(Context context) {

        if(ourInstance==null){
            ourInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "bookmark-database").build();
        }
        return ourInstance;
    }

    public static void destroyInstance() {
        ourInstance = null;
    }

}
