package com.vivek.omdb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.vivek.omdb.api.ApiServiceInterface;
import com.vivek.omdb.api.RetrofitRestClient;
import com.vivek.omdb.db.BookmarkRepository;
import com.vivek.omdb.model.Movie;
import com.vivek.omdb.model.RestApiError;
import com.vivek.omdb.model.Search;
import com.vivek.omdb.model.SearchListRepo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragmentViewModel extends AndroidViewModel {
    private ApiServiceInterface apiServiceInterface;
    private String TAG = MainFragmentViewModel.class.getSimpleName();
    private BookmarkRepository bookmarkRepository;
    private MutableLiveData<List<Movie>> mainLiveData = new MutableLiveData<>();
    private MutableLiveData<String> apiError = new MutableLiveData<>();

    private LiveData<List<Movie>> bookMarks = new MutableLiveData<>();


    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        apiServiceInterface = RetrofitRestClient.createService(ApiServiceInterface.class, application.getApplicationContext());
        bookmarkRepository = new BookmarkRepository(application.getApplicationContext());
        bookMarks = bookmarkRepository.getAllFav();
    }



    public void getMovies(String title, String page) {
        apiServiceInterface.getMovies(title, page).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                List<Movie> movieList = new ArrayList<>();
                if (response.isSuccessful() && response.body() != null) {
                    Gson gson = new GsonBuilder().create();
                    SearchListRepo searchListRepo = gson.fromJson(response.body(), SearchListRepo.class);
                    if(searchListRepo.getResponse().equalsIgnoreCase("true")){
                        List<Search> searchList = searchListRepo.getSearch();
                        for (Search movie : searchList) {
                            movieList.add(new Movie(movie.getImdbID(), movie.getTitle(), movie.getYear(), movie.getPoster()));
                        }
                        mainLiveData.setValue(movieList);
                    }else{
                        RestApiError error = gson.fromJson(response.body(), RestApiError.class);
                        apiError.setValue(error.getError());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                apiError.setValue(t.getMessage());
            }
        });
    }


    public MutableLiveData<List<Movie>> getMainLiveData() {
        return mainLiveData;
    }

    public LiveData<List<Movie>> getBookMarks() {
        return bookMarks;
    }

    public MutableLiveData<String> getApiError() {
        return apiError;
    }
}
