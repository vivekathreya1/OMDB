package com.vivek.omdb.view.activity;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.vivek.omdb.R;
import com.vivek.omdb.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
