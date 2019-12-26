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
import com.vivek.omdb.databinding.BookmarkItemBinding;
import com.vivek.omdb.db.BookmarkRepository;
import com.vivek.omdb.model.Movie;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private Context context;
    private List<Movie> bookMark;
    private BookmarkRepository bookmarkRepository;

    public BookmarkAdapter(Context context, List<Movie> bookMark) {
        this.context = context;
        this.bookMark = bookMark;
        bookmarkRepository = new BookmarkRepository(context);
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookmarkItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bookmark_item, parent, false);
        return new BookmarkViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        holder.bind(bookMark.get(position));
    }

    @Override
    public int getItemCount() {
        return bookMark.size();
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder{

        BookmarkItemBinding binding;

        public BookmarkViewHolder( BookmarkItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setViewHolder(this);
        }

        public void bind(Object obj) {
            binding.setVariable(BR.bookMarkObj, obj);
            binding.executePendingBindings();
        }

        public void onClickRow(Movie movie){
            Bundle bundle = new Bundle();
            bundle.putString("imdbId", movie.getImdbId());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_mainFragment_to_movieDetails, bundle);
        }

        public void onClickRemoveBookmark(Movie model) {
            int existId = bookmarkRepository.checkIfImdbIdExists(model.getImdbId());
                bookmarkRepository.deleteFav(existId);

        }

    }
}
