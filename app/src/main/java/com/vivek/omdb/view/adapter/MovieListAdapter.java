package com.vivek.omdb.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.vivek.omdb.R;
import com.vivek.omdb.databinding.SearchItemRowBinding;
import com.vivek.omdb.db.BookmarkRepository;
import com.vivek.omdb.model.Movie;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.RecyclerViewHolder> {

    private Context context;
    private List<Movie> movieList;
    private BookmarkRepository bookmarkRepository;

    public MovieListAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        bookmarkRepository = new BookmarkRepository(context);



    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.search_item_row, parent, false);
        return new RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(movieList.get(position));
        holder.getBookMarked(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        SearchItemRowBinding binding;

        public RecyclerViewHolder( SearchItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setViewHolder(this);
        }

        public void bind(Object obj) {
            binding.setVariable(BR.movieObj, obj);
            binding.executePendingBindings();
        }

        public void getBookMarked(Movie model){
            int existId = bookmarkRepository.checkIfImdbIdExists(model.getImdbId());
            if (existId > 0) {
                binding.favIv.setImageDrawable(context.getDrawable(R.drawable.heart_gold));
            } else {
                binding.favIv.setImageDrawable(context.getDrawable(R.drawable.heart_outline));
            }
        }

        public void onClickRow(Movie movie){
            Bundle bundle = new Bundle();
            bundle.putString("imdbId", movie.getImdbId());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_mainFragment_to_movieDetails, bundle);
        }

        public void onClickAddBookmark(Movie model) {
            int existId = bookmarkRepository.checkIfImdbIdExists(model.getImdbId());
            if (existId <= 0) {
                bookmarkRepository.insert(model);
                binding.favIv.setImageDrawable(context.getDrawable(R.drawable.heart_gold));
            } else {
                bookmarkRepository.deleteFav(existId);
                binding.favIv.setImageDrawable(context.getDrawable(R.drawable.heart_outline));
            }

        }

    }
}
