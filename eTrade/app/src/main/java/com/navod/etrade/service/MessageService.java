package com.navod.etrade.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.navod.etrade.callback.GetIndividualMessageListCallback;
import com.navod.etrade.callback.IndividualMessageAddedCallback;
import com.navod.etrade.callback.IndividualMessageUpdatedCallback;
import com.navod.etrade.entity.IndividualMessage;
import com.navod.etrade.util.CurrentDate;
import com.navod.etrade.util.NotificationHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageService {
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private Context context;
    public MessageService(String userId, Context context){
        this.db = FirebaseFirestore.getInstance();
        this.collectionReference = db.collection("user").document(userId).collection("messages");
        this.context = context;
    }
    public void getMessages(GetIndividualMessageListCallback callback){
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    callback.onFailure("Error on getting real-time Messages: " + error.getMessage());
                    return;
                }

                if (snapshot != null) {
                    List<IndividualMessage> individualMessages = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : snapshot) {
                        IndividualMessage individualMessage = doc.toObject(IndividualMessage.class);
                        individualMessages.add(individualMessage);
                    }
                    callback.onSuccess(individualMessages);
                }
            }
        });
    }
    public void addMessage(IndividualMessage individualMessage, IndividualMessageAddedCallback callback){
        collectionReference.add(individualMessage)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        HashMap<String, Object> updateCollection = new HashMap<>();
                        updateCollection.put("messageId", documentReference.getId());
                        updateCollection.put("addedDate", CurrentDate.getCurrentDate());

                        updateMessage(documentReference.getId(), updateCollection, new IndividualMessageUpdatedCallback() {
                            @Override
                            public void update(boolean success) {
                                if(success){
                                    notifications(individualMessage);
                                }else{
                                    callback.onFailure("Update Failed");
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure("message adding failed "+e.getMessage());
                    }
                });
    }
    public void updateMessage(String documentId, HashMap<String, Object> updateCollection, IndividualMessageUpdatedCallback callback){
        collectionReference.document(documentId)
                .update(updateCollection)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.update(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.update(false);
                    }
                });
    }
    public void notifications(IndividualMessage individualMessage){
        NotificationHelper.createNotificationChannel(context);
        NotificationHelper.sendNotification(context, individualMessage.getTitle(), individualMessage.getMessageId());
    }
}
