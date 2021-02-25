package com.example.rxjava.view_models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rxjava.models.Post;
import com.example.rxjava.reprositories.PostRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {
    PostRepository repository;
    CompositeDisposable disposable;
    MutableLiveData<List<Post>> livePosts;

    public MainActivityViewModel() {
        disposable = new CompositeDisposable();
        repository = new PostRepository();
        livePosts = new MutableLiveData<>();
    }

    public void loadPosts() {
        disposable.add(repository.getPosts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(posts -> {
                    livePosts.setValue(posts);
                }));
    }

    public LiveData<List<Post>> getLivePosts() {
        loadPosts();
        return livePosts;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
