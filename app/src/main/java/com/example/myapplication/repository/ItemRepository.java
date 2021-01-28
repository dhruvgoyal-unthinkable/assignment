package com.example.myapplication.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.myapplication.daos.ItemDao;
import com.example.myapplication.database.AppDB;
import com.example.myapplication.models.Item;

import java.util.List;
import java.util.Objects;

public class ItemRepository {

    private final ItemDao itemDao;
    private final LiveData<List<Item>> items;
    private final LiveData<Integer> total;

    public ItemRepository(Application application) {
        itemDao = AppDB.getINSTANCE(application).itemDao();
        items = itemDao.getItems();
        total = itemDao.getTotal();
    }

    public void insertItem(Item item) {
        new insertAsyncTask(itemDao).execute(item);
    }


    public LiveData<List<Item>> getItems() {
        return items;
    }

    public LiveData<Integer> getTotal() {
        return total;
    }


    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {
        private final ItemDao itemDao;

        private insertAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.insertItem(items[0]);
            return null;
        }
    }
}
