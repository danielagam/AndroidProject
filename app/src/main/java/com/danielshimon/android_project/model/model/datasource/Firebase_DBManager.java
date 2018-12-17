package com.danielshimon.android_project.model.model.datasource;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.danielshimon.android_project.model.model.backend.Backend;
import com.danielshimon.android_project.model.model.backend.BackendFactory;
import com.danielshimon.android_project.model.model.entities.Travel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/**
 * FireBase manager this app just need to write data
 */
public class Firebase_DBManager implements Backend {
    //for the singleton just BackendFactory can create Firebase_DBManager
    public Firebase_DBManager(BackendFactory.Friend f) {
        //if try to create Firebase_DBManager with null parameter
        //this throw NullPointerException
        f.hashCode();
    }
    //the database reference
    private DatabaseReference clientsRef = FirebaseDatabase.getInstance().getReference("clients");
    //the write function
    public void addRequest(Travel travel, final Context context) {
        clientsRef.push().setValue(travel).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(context, "הבקשה נשלחה בהצלחה", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "בקשתך נכשלה", Toast.LENGTH_LONG).show();
            }
        });
    }
}


