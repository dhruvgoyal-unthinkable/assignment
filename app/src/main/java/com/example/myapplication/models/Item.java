package com.example.myapplication.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String item_name;
    private final double item_price;

    public Item(String item_name, double item_price) {
        this.item_name = item_name;
        this.item_price = item_price;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getItem_name() {
        return item_name;
    }

    public double getItem_price() {
        return item_price;
    }
}
