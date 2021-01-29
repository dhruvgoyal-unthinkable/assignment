package com.example.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
    static ItemViewModel itemViewModel;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = MainActivity.this;
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        ItemListAdapter itemListAdapter = new ItemListAdapter();

        binding.itemsListView.setLayoutManager(new LinearLayoutManager(this));
        binding.itemsListView.setHasFixedSize(true);
        binding.itemsListView.setAdapter(itemListAdapter);

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.getItems().observe(this, itemListAdapter::updateList);
        itemViewModel.getTotal().observe(this, integer -> {
            binding.total.setText(String.format("%s %d", getString(R.string.total), (integer == null) ? 0 : integer));
        });

        binding.addData.setOnClickListener(v -> {
            showDialog(null);
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                itemViewModel.deleteItem(itemListAdapter.getItem(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(binding.itemsListView);
    }

    public static void showDialog(Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        CustomInputDialogBinding customInputDialogBinding = CustomInputDialogBinding.inflate(layoutInflater);
        builder.setView(customInputDialogBinding.getRoot());
        if(item != null){
            customInputDialogBinding.itemName.setText(item.getItem_name());
            customInputDialogBinding.itemPrice.setText(Double.toString(item.getItem_price()));
        }

        builder.setPositiveButton((item == null) ? "Add" : "Update", (dialog, which) -> {
            String itemName = Objects.requireNonNull(customInputDialogBinding.itemName.getText()).toString();
            String itemPriceText = Objects.requireNonNull(customInputDialogBinding.itemPrice.getText()).toString().trim();
            double itemPrice = Double.parseDouble(itemPriceText);
            if (item == null) {
                itemViewModel.insertItem(new Item(itemName, itemPrice));
            } else {
                Item updatedItem = new Item(itemName, itemPrice);
                updatedItem.setId(item.getId());
                itemViewModel.updateItem(updatedItem);
            }
        });

        builder.setNegativeButton("Cancel", ((dialog, which) -> {
            dialog.dismiss();
        }));

        builder.show();
    }
}