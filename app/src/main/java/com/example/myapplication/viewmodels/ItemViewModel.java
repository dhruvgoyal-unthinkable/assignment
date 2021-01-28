package com.example.myapplication.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.models.Item;
import com.example.myapplication.repository.ItemRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private LiveData<List<Item>> items;
    private LiveData<Integer> total;
    private ItemRepository itemRepository;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
        items = itemRepository.getItems();
        total = itemRepository.getTotal();
    }

    public void insertItem(Item item){
        itemRepository.insertItem(item);
    }

    public LiveData<List<Item>> getItems(){
        return items;
    }
    public LiveData<Integer> getTotal(){
        return total;
    }
}
