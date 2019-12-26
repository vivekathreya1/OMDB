package com.vivek.omdb.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vivek.omdb.R;
import com.vivek.omdb.databinding.FragmentMainBinding;
import com.vivek.omdb.model.Movie;
import com.vivek.omdb.view.adapter.BookmarkAdapter;
import com.vivek.omdb.view.adapter.MovieListAdapter;
import com.vivek.omdb.viewmodel.MainFragmentViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {

    private FragmentMainBinding binding;
    private View rootView;
    private MainFragmentViewModel viewModel;
    private List<Movie> movieList;
    private List<Movie> bookMarkList;
    private MovieListAdapter movieListAdapter;
    private BookmarkAdapter bookmarkAdapter;
    boolean isLoading = false;

    private int pageNo;
    private String searchString;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieList = new ArrayList<>();
        bookMarkList = new ArrayList<>();
        pageNo = 1;
        searchString = "The pirates of caribbean";
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        viewModel.getMovies(searchString, String.valueOf(pageNo));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        viewModel.getMainLiveData().removeObservers(this);
        binding.setViewModel(viewModel);
//        binding.setLifecycleOwner(this);
        rootView = binding.getRoot();
        initUi();
        return rootView;
    }

    private void initUi(){
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setRecyclerView();
        setListener();
        setObservers();
        initScrollListener();
    }

    private void setListener() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty()){
                    searchString = query;
                    pageNo = 1;
                    movieList.clear();
                    viewModel.getMovies(query, String.valueOf(pageNo));
                    binding.errorTv.setVisibility(View.GONE);
                    binding.loadingProgressBar.setVisibility(View.VISIBLE);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    private void setObservers() {
        viewModel.getApiError().observe(this, s -> {
            if(!s.isEmpty()){
                binding.errorTv.setVisibility(View.VISIBLE);
                binding.errorTv.setText(s);
                binding.searchResutlsTv.setVisibility(View.INVISIBLE);
                binding.loadingProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        viewModel.getBookMarks().observe(this, movies -> {
            if (movies != null) {
                bookMarkList.clear();
                bookMarkList.addAll(movies);
                bookmarkAdapter.notifyDataSetChanged();
                movieListAdapter.notifyDataSetChanged();

            }
        });

        viewModel.getMainLiveData().observe(this, movies -> {
            if(movies!=null){
                if(movies.size()<10){
                    binding.loadingProgressBar.setVisibility(View.GONE);
                }else{
                    binding.loadingProgressBar.setVisibility(View.VISIBLE);
                }
                isLoading = false;
                movieList.addAll(movies);
                movieListAdapter.notifyDataSetChanged();
                binding.errorTv.setVisibility(View.GONE);
                binding.searchResutlsTv.setVisibility(View.VISIBLE);
                viewModel.getMainLiveData().setValue(null);
            }

            if(movieList.size()<10){
                binding.loadingProgressBar.setVisibility(View.GONE);
            }else{
                binding.loadingProgressBar.setVisibility(View.VISIBLE);
            }

        });

    }

    private void setRecyclerView() {
        movieListAdapter = new MovieListAdapter(getActivity(), movieList);
        binding.mainRv.setAdapter(movieListAdapter);
        binding.mainRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        ViewCompat.setNestedScrollingEnabled(binding.mainRv, false);

        bookmarkAdapter = new BookmarkAdapter(getActivity(), bookMarkList);
        binding.bookmarkRv.setAdapter(bookmarkAdapter);
        binding.bookmarkRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void initScrollListener() {

        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())){
                    if (!isLoading ) {
                            viewModel.getMovies(searchString, String.valueOf(++pageNo));
                            isLoading = true;
                    }
                }
            }
        });
        /*binding.mainRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading & dy != 0) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == movieList.size() - 1) {
                        viewModel.getMovies(searchString, String.valueOf(++pageNo));
                        isLoading = true;
                    }
                }
            }
        });*/


    }

}
