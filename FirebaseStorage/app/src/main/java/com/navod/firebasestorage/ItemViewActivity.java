package com.navod.firebasestorage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.navod.firebasestorage.adapter.ItemAdapter;
import com.navod.firebasestorage.model.Item;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class ItemViewActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        items = new ArrayList<>();

        RecyclerView itemView = findViewById(R.id.itemView);

        ItemAdapter itemAdapter = new ItemAdapter(items, ItemViewActivity.this);
//        If you want it as a grid
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
//        itemView.setLayoutManager(gridLayoutManager);
        //        If you want it as a grid
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        itemView.setLayoutManager(linearLayoutManager);
        itemView.setAdapter(itemAdapter);

        firestore.collection("Items").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        items.clear();
                        for(QueryDocumentSnapshot snapshot : task.getResult()){
                            Item item = snapshot.toObject(Item.class);
                            items.add(item);
                        }
                        itemAdapter.notifyDataSetChanged();
                    }
                });

        firestore.collection("items").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                for(DocumentSnapshot snapshot : value.getDocuments()){
//                    Item item = snapshot.toObject(Item.class);
//                    items.add(item);
//                }

                for(DocumentChange change : value.getDocumentChanges()){
                    Item item = change.getDocument().toObject(Item.class);
                    switch (change.getType()){
                        case ADDED:
                            items.add(item);
                        case MODIFIED:
                            Item old = items.stream().filter(i -> item.getName().equals(item.getName())).findFirst().orElse(null);
                            if(old!=null){
                                old.setDescription(item.getDescription());
                                old.setPrice(item.getPrice());
                                old.setImage(item.getImage());
                            }
                            break;
                        case REMOVED:
                            items.remove(item);
                    }
                }
                itemAdapter.notifyDataSetChanged();
            }
        });
    }
}