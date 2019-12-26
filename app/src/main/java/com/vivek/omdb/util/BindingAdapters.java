package com.vivek.omdb.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;
import com.vivek.omdb.R;

public class BindingAdapters {

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get().load(imageUrl).placeholder(R.drawable.placeholder).into(view);
    }




}
