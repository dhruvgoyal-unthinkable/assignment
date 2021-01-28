package com.example.myapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.daos.ItemDao;
import com.example.myapplication.models.Item;

@Database(entities = {Item.class}, version = 1)
public abstract class AppDB extends RoomDatabase {

    private static AppDB INSTANCE;
    public abstract ItemDao itemDao();
    public static synchronized AppDB getINSTANCE(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext()
            ,AppDB.class,"app_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
