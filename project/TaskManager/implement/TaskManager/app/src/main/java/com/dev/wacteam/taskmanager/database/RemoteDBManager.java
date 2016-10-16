package com.dev.wacteam.taskmanager.database;

import com.dev.wacteam.taskmanager.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by giuse96suoire on 10/12/2016.
 */
public abstract class RemoteDBManager {

    private DatabaseReference mDatabase;

    public RemoteDBManager() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void mWrite(String child, Object value) {
        mDatabase.child(child).setValue(value);
    }

    public void mDelete(String child) {
        mDatabase.child(child).removeValue();
    }


}
