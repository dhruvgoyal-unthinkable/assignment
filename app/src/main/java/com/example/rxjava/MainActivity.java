package com.example.rxjava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rxjava.adapter.PostsAdapter;
import com.example.rxjava.databinding.ActivityMainBinding;
import com.example.rxjava.view_models.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainActivityViewModel model = new ViewModelProvider(this).get(MainActivityViewModel.class);
        PostsAdapter adapter = new PostsAdapter();
        binding.posts.setHasFixedSize(true);
        binding.posts.setLayoutManager(new LinearLayoutManager(this));
        binding.posts.setAdapter(adapter);
        model.getLivePosts().observe(this, adapter::updatePosts);
    }
}