package com.navod.marketapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.navod.marketapp.adapter.MyAdapter;
import com.navod.marketapp.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Item> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        setRecyclerView();
    }

    private void setRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new MyAdapter(getItems()));
    }

    private List<Item> getItems() {
        itemList = new ArrayList<>();
        itemList.add(new Item(R.drawable.fruit, "Fruits", "Nature's candy"));
        itemList.add(new Item(R.drawable.beverage, "Beverage", "Quench your thirst"));
        itemList.add(new Item(R.drawable.bread, "Bread", "The staff of life"));
        itemList.add(new Item(R.drawable.milk, "Milk", "Nature's perfect food"));
        itemList.add(new Item(R.drawable.popcorn, "Popcorn", "A movie night essential"));
        itemList.add(new Item(R.drawable.vegitables, "vegetables", "Eat your greens"));
        return itemList;
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
    }
}