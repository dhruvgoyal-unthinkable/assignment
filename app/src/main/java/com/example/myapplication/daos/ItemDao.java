package com.example.myapplication.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.models.Item;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insertItem(Item item);

    @Query("SELECT * FROM Item")
    LiveData<List<Item>> getItems();

    @Query("SELECT SUM(item_price) FROM ITEM")
    LiveData<Integer> getTotal();

    @Delete
    void deleteItem(Item item);

    @Update
    void updateItem(Item item);
}
