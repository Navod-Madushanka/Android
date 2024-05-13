package com.navod.volumeareaapp;

import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Shape> shapesArrayList;
    MyCustomAdaptor adaptor;

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
        getDataSource();
        setGridView();
    }

    private void setGridView(){
        adaptor = new MyCustomAdaptor(shapesArrayList, getApplicationContext());
        gridView.setAdapter(adaptor);
        gridView.setNumColumns(2);
    }

    private void getDataSource(){
        shapesArrayList = new ArrayList<>();
        Shape spear = new Shape(R.drawable.sphere, "spear");
        Shape cylinder = new Shape(R.drawable.cylinder, "cylinder");
        Shape cube = new Shape(R.drawable.cube, "cube");
        Shape prism = new Shape(R.drawable.prism, "prism");

        shapesArrayList.add(spear);
        shapesArrayList.add(cylinder);
        shapesArrayList.add(cube);
        shapesArrayList.add(prism);
    }

    private void init(){
        gridView = findViewById(R.id.gridView);
    }
}