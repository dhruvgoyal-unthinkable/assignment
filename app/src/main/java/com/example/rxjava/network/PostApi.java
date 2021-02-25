package com.example.rxjava.network;

import com.example.rxjava.models.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface PostApi {

    @GET("posts")
    public Flowable<List<Post>> getPosts();
}
