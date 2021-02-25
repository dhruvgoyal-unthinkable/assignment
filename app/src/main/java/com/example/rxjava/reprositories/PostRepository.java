package com.example.rxjava.reprositories;

import com.example.rxjava.models.Post;
import com.example.rxjava.network.PostApi;
import com.example.rxjava.network.RetrofitClient;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.Retrofit;

public class PostRepository {

    public Flowable<List<Post>> getPosts() {
        Retrofit retrofit = RetrofitClient.getInstance();
        PostApi api = retrofit.create(PostApi.class);
        return api.getPosts();
    }
}
