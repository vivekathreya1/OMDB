package com.vivek.omdb.view.fragment;


import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.vivek.omdb.R;
import com.vivek.omdb.databinding.FragmentMovieDetailsBinding;
import com.vivek.omdb.viewmodel.MovieDetailsViewModel;

import static android.graphics.Typeface.BOLD;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends BaseFragment {


    private String imdbId;
    private FragmentMovieDetailsBinding binding;
    private View rootView;
    private MovieDetailsViewModel viewModel;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        imdbId = getArguments().getString("imdbId");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        rootView = binding.getRoot();
        setObservers();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewModel.getMovieDetails(imdbId, "full");
        return rootView;
    }



    private void setObservers(){
        viewModel.director.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if(s!= null && !s.isEmpty()){
                    int noOfDirectors = 1;
                    if(s.contains(",")){
                        noOfDirectors = 2;
                    }
                    binding.director.setText(beautifytext(getResources().getQuantityString(R.plurals.director, noOfDirectors), s));

                }

                binding.progressBar.setVisibility(View.GONE);
                binding.errorTv.setVisibility(View.INVISIBLE);
                binding.scrollView.setVisibility(View.VISIBLE);
            }
        });

        viewModel.actors.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!= null && !s.isEmpty()){
                    int noOfActors = 1;
                    if(s.contains(",")){
                        noOfActors = 2;
                    }
                    binding.actors.setText(beautifytext(getResources().getQuantityString(R.plurals.actor, noOfActors), s));

                }
            }
        });

        viewModel.writer.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!= null && !s.isEmpty()){
                    int noOfWriters = 1;
                    if(s.contains(",")){
                        noOfWriters = 2;
                    }
                    binding.writer.setText(beautifytext(getResources().getQuantityString(R.plurals.writer, noOfWriters), s));

                }
            }
        });

        viewModel.getApiError().observe(this,s -> {
            binding.errorTv.setText(s);
            binding.errorTv.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
            binding.scrollView.setVisibility(View.INVISIBLE);
        });

    }

    private SpannableStringBuilder beautifytext(String name, String val){
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(name + "\n").setSpan(new StyleSpan(BOLD), 0, name.length(), 0);
        spannableStringBuilder.append(val);
        return spannableStringBuilder;

    }


}
