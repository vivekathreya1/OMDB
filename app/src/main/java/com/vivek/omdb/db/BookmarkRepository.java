package com.vivek.omdb.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.vivek.omdb.model.Movie;

import java.util.List;

public class BookmarkRepository {

    private BookmarkDao dao;

    public BookmarkRepository(Context context) {
        AppDatabase db = AppDatabase.getAppDatabase(context);
        dao = db.bookmarkDao();
    }

    public LiveData<List<Movie>> getAllFav() {
        return dao.getBookmarkList();
    }



    public int checkIfImdbIdExists(String imdbId){
        CheckIfTitleExistsInOtherRowAsyncTask task = new CheckIfTitleExistsInOtherRowAsyncTask(dao);
        int id = -1;
        try {
            id = task.execute(imdbId).get();
        } catch (Exception e) {
        }
        return id;

    }


    private static class CheckIfTitleExistsInOtherRowAsyncTask extends AsyncTask<String, Void, Integer> {
        private BookmarkDao mAsyncTaskDao;
        CheckIfTitleExistsInOtherRowAsyncTask(BookmarkDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Integer doInBackground(final String... params) {
            return mAsyncTaskDao.ifimdbIdExists(params[0]);
        }
    }


    public Long insert(Movie movie) {
        InsertAsyncTask task = new InsertAsyncTask(dao);
        Long id = null;
        try {
            id = task.execute(movie).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    private static class InsertAsyncTask extends AsyncTask<Movie, Void, Long> {
        private BookmarkDao mAsyncTaskDao;

        InsertAsyncTask(BookmarkDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Movie... params) {
            return mAsyncTaskDao.insert(params[0]);
        }
    }

    public int deleteFav(int inputId){
        DeleteFavAsyncTask task = new DeleteFavAsyncTask(dao);
        int rows = -1;
        try {
            rows = task.execute(inputId).get();
        } catch (Exception e) {
        }
        return rows;
    }

    private static class DeleteFavAsyncTask extends AsyncTask<Integer, Void, Integer> {
        private BookmarkDao mAsyncTaskDao;

        DeleteFavAsyncTask(BookmarkDao favDao) {
            mAsyncTaskDao = favDao;
        }

        @Override
        protected Integer doInBackground(Integer... favs) {
            return mAsyncTaskDao.deleteFav(favs[0]);
        }
    }


}
