package com.vivek.omdb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.vivek.omdb.api.ApiServiceInterface;
import com.vivek.omdb.api.RetrofitRestClient;
import com.vivek.omdb.model.MovieDetailRepo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsViewModel extends AndroidViewModel {
    private ApiServiceInterface apiServiceInterface;
    private String TAG = MovieDetailsViewModel.class.getSimpleName();
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> year = new MutableLiveData<>();
    public MutableLiveData<String> rated = new MutableLiveData<>();
    public MutableLiveData<String> runtime = new MutableLiveData<>();
    public MutableLiveData<String> language = new MutableLiveData<>();
    public MutableLiveData<String> plot = new MutableLiveData<>();
    public MutableLiveData<String> imdbRating = new MutableLiveData<>();
    public MutableLiveData<String> rottenTomRating = new MutableLiveData<>();
    public MutableLiveData<String> director = new MutableLiveData<>();
    public MutableLiveData<String> actors = new MutableLiveData<>();
    public MutableLiveData<String> writer = new MutableLiveData<>();
    public MutableLiveData<String> posterLink = new MutableLiveData<>();
    private MutableLiveData<String> apiError = new MutableLiveData<>();


    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        apiServiceInterface = RetrofitRestClient.createService(ApiServiceInterface.class, application.getApplicationContext());
    }

    public void getMovieDetails(String imdbId, String plotLength) {
        apiServiceInterface.getMovieDetails(imdbId, plotLength).enqueue(new Callback<MovieDetailRepo>() {
            @Override
            public void onResponse(Call<MovieDetailRepo> call, Response<MovieDetailRepo> response) {
                MovieDetailRepo movieDetail = response.body();
                title.setValue(movieDetail.getTitle());
                year.setValue(movieDetail.getYear());
                rated.setValue(movieDetail.getRated());
                runtime.setValue(movieDetail.getRuntime());
                language.setValue(movieDetail.getLanguage());
                plot.setValue(movieDetail.getPlot());
                imdbRating.setValue(movieDetail.getImdbRating() + "/10");
                director.setValue(movieDetail.getDirector());
                actors.setValue(movieDetail.getActors());
                writer.setValue(movieDetail.getWriter());
                posterLink.setValue(movieDetail.getPoster());


            }

            @Override
            public void onFailure(Call<MovieDetailRepo> call, Throwable t) {
                apiError.setValue(t.getMessage());
            }
        });
    }

    public MutableLiveData<String> getApiError() {
        return apiError;
    }
}
