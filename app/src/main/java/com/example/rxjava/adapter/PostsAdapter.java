package com.example.rxjava.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rxjava.R;
import com.example.rxjava.databinding.CustomPostLayoutBinding;
import com.example.rxjava.models.Post;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {
    List<Post> posts = new ArrayList<>();

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(CustomPostLayoutBinding.bind(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_post_layout, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.binding.postId.setText(Integer.toString(post.getId()) + ". ");
        holder.binding.body.setText(post.getBody());
        holder.binding.title.setText(post.getTitle());
    }

    public void updatePosts(List<Post> posts) {
        this.posts.clear();
        this.posts.addAll(posts);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        CustomPostLayoutBinding binding;

        public PostViewHolder(CustomPostLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
