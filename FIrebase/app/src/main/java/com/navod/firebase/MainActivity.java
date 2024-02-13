package com.navod.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.navod.firebase.model.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.i(TAG, db.toString());
        findViewById(R.id.btnLoadData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listenerRegistration == null){
                    realTimeUpdatesDemo(db);
                }
            }
        });
        findViewById(R.id.btnDeleteDoc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDocument(db);
            }
        });
    }
    private void deleteDocument(FirebaseFirestore db){
        db.collection("cities").document("DC").collection("infos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot snapshot : task.getResult()){
                            snapshot.getReference().delete();
//                            or
                            db.collection("cities/DC").document(snapshot.getId())
                                    .delete();
//                            or
                            db.document("cities/DC/infos/"+snapshot.getId()).delete();
                        }
                    }
                });

        db.document("cities/DC").delete();
        db.collection("cities").document("DC").delete();

//        db.collection("cities").document("DC")
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
    }
    private void realTimeUpdatesDemo(FirebaseFirestore db){
        listenerRegistration = db.collection("cities").whereEqualTo("country", "Japan")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(!value.isEmpty()){
                            List<DocumentSnapshot> documents = value.getDocuments();
                            documents.forEach(document->{
                                Log.i(TAG, document.getString("name"));
                                setTextWithName(document.getString("name"));
                            });
                        }
                    }
                });
        listenerRegistration.remove();
    }
    private void getByCountry(FirebaseFirestore db){
        db.collection("cities").whereEqualTo("country", "Japan")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot snapshot:task.getResult()){
                                Log.i(TAG, snapshot.getString("name"));
                                setTextWithName(snapshot.getString("name"));
                            }
                        }
                    }
                });
    }
    private void setTextWithName(String name){
        TextView textView = findViewById(R.id.textView);
        textView.setText(name);
    }
    private void getCitiesWithCapitalTrue(FirebaseFirestore db){
        db.collection("cities").whereEqualTo("capital", true)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot snapshot:task.getResult()){
                                Log.i(TAG, snapshot.getString("name"));
                            }
                        }
                    }
                });
    }
    private void getCities(FirebaseFirestore db){
        Source server = Source.SERVER;
        DocumentReference docRef = db.collection("cities").document("SF");
        docRef.get(server).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    private void saveCities(FirebaseFirestore db){

        CollectionReference cities = db.collection("cities");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "San Francisco");
        data1.put("state", "CA");
        data1.put("country", "USA");
        data1.put("capital", false);
        data1.put("population", 860000);
        data1.put("regions", Arrays.asList("west_coast", "norcal"));
        cities.document("SF").set(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "Los Angeles");
        data2.put("state", "CA");
        data2.put("country", "USA");
        data2.put("capital", false);
        data2.put("population", 3900000);
        data2.put("regions", Arrays.asList("west_coast", "socal"));
        cities.document("LA").set(data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("name", "Washington D.C.");
        data3.put("state", null);
        data3.put("country", "USA");
        data3.put("capital", true);
        data3.put("population", 680000);
        data3.put("regions", Arrays.asList("east_coast"));
        cities.document("DC").set(data3);

        Map<String, Object> data4 = new HashMap<>();
        data4.put("name", "Tokyo");
        data4.put("state", null);
        data4.put("country", "Japan");
        data4.put("capital", true);
        data4.put("population", 9000000);
        data4.put("regions", Arrays.asList("kanto", "honshu"));
        cities.document("TOK").set(data4);

        Map<String, Object> data5 = new HashMap<>();
        data5.put("name", "Beijing");
        data5.put("state", null);
        data5.put("country", "China");
        data5.put("capital", true);
        data5.put("population", 21500000);
        data5.put("regions", Arrays.asList("jingjinji", "hebei"));
        cities.document("BJ").set(data5);
    }
    private void getUserAsDocument(FirebaseFirestore db){
        db.document("/users/1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot result = task.getResult();
//                    or
                    User user = task.getResult().toObject(User.class);
                }
            }
        });
    }
    private void getUser(FirebaseFirestore db){
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot result : task.getResult()){
                        String name = result.getString("name");
                        Log.i(TAG, "Name: "+name);
                    }
//                    QuerySnapshot result = task.getResult();
//                    List<DocumentSnapshot> documents = result.getDocuments();
//                    documents.forEach(u->{
//                        String name = u.getString("name");
//                        Log.i(TAG, "Name: "+name);
//                    });
                }
            }
        });
    }
    private void saveUser(FirebaseFirestore db){
        User user = new User();
        user.setName("Hasitha");
        user.setEmail("hasitha@gmail.com");
        user.setContact("0744410116");
        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.i(TAG, documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "Error"+e);
            }
        });
    }
}