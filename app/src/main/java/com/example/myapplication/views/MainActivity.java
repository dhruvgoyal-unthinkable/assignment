package com.example.myapplication.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ItemListAdapter;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.CustomInputDialogBinding;
import com.example.myapplication.models.Item;
import com.example.myapplication.viewmodels.ItemViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        ItemListAdapter itemListAdapter = new ItemListAdapter();

        binding.itemsListView.setLayoutManager(new LinearLayoutManager(this));
        binding.itemsListView.setHasFixedSize(true);
        binding.itemsListView.setAdapter(itemListAdapter);

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.getItems().observe(this, itemListAdapter::updateList);
        itemViewModel.getTotal().observe(this, integer -> {
            binding.total.setText(String.format("%s %d", getString(R.string.total), (integer == null)?0:integer));
        });

        binding.addData.setOnClickListener(v -> {
            showDialog();
        });
    }

    void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        CustomInputDialogBinding customInputDialogBinding = CustomInputDialogBinding.inflate(layoutInflater);
        builder.setView(customInputDialogBinding.getRoot());

        builder.setPositiveButton("Add", (dialog, which) -> {
           String itemName = Objects.requireNonNull(customInputDialogBinding.itemName.getText()).toString();
           String itemPriceText = Objects.requireNonNull(customInputDialogBinding.itemPrice.getText()).toString().trim();
           double itemPrice = Double.parseDouble(itemPriceText);
           itemViewModel.insertItem(new Item(itemName,itemPrice));
        });

        builder.setNegativeButton("Cancel",((dialog, which) -> {
            dialog.dismiss();
        }));

        builder.show();
    }
}