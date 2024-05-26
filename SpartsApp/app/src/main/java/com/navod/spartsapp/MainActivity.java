package com.navod.spartsapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.navod.spartsapp.adapter.CustomAdapter;
import com.navod.spartsapp.model.Sport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

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
        setAdapter();
    }

    private void setAdapter(){
        CustomAdapter customAdapter = new CustomAdapter(getDataSource());
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(customAdapter);
    }

    private List<Sport> getDataSource(){
        List<Sport> sports = new ArrayList<>();
        sports.add(new Sport("Football", R.drawable.football));
        sports.add(new Sport("Basketball", R.drawable.basketball));
        sports.add(new Sport("Tennis", R.drawable.tennis));
        sports.add(new Sport("Volleyball", R.drawable.volley));
        return sports;
    }

    private void init(){
        recyclerView = findViewById(R.id.recyclerView);
    }
}