package com.vivek.omdb.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vivek.omdb.model.Movie;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Movie movie);

    @Query("SELECT imdbId from bookmarkTable")
    List<String> getBookmarkImdbIdList();

    @Query("SELECT * from bookmarkTable")
    LiveData<List<Movie>> getBookmarkList();

    @Query("SELECT  id from bookmarkTable where imdbId= :imdbId")
    int ifimdbIdExists(String imdbId);

    @Query("DELETE from bookmarkTable where id = :id")
    int deleteFav(int id);
}
