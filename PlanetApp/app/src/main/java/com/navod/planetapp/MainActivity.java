package com.navod.planetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Planet> planetArrayList;

    private MyCustomAdapter myCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        new GetDataSourceTask().execute();
    }
    private class GetDataSourceTask extends AsyncTask<Void, Void, ArrayList<Planet>> {

        @Override
        protected ArrayList<Planet> doInBackground(Void... voids) {
            // Get the data source on a separate thread
            ArrayList<Planet> planetArrayList = new ArrayList<>();
            planetArrayList.add(new Planet("Mercury", "0", R.drawable.mercury));
            planetArrayList.add(new Planet("Venus", "0", R.drawable.venus));
            planetArrayList.add(new Planet("Earth", "1", R.drawable.earth));
            planetArrayList.add(new Planet("Mars", "2", R.drawable.mars));
            planetArrayList.add(new Planet("Jupiter", "79", R.drawable.jupiter));
            planetArrayList.add(new Planet("Saturn", "82", R.drawable.saturn));
            planetArrayList.add(new Planet("Uranus", "27", R.drawable.uranus));
            planetArrayList.add(new Planet("Neptune", "14", R.drawable.neptune));

            return planetArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Planet> planetArrayList) {
            // Set the adapter on the main thread
            myCustomAdapter = new MyCustomAdapter(MainActivity.this, planetArrayList);
            listView.setAdapter(myCustomAdapter);
        }
    }

    private void init(){
        listView = findViewById(R.id.listVew);
    }
}