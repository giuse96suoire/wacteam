package com.dev.wacteam.taskmanager.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by giuse96suoire on 10/12/2016.
 */
public abstract class RemoteDatabase {

    private DatabaseReference mDatabase;

    public RemoteDatabase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void mWriteIfNotExist(String child, Object value) {
        mDatabase.child(child).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object object = dataSnapshot.getValue(Object.class);
                if(object != null){
                    mWrite(child, value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO: error handler
                System.out.println(databaseError.getMessage()+" ERROR ---------------------->");
            }
        });
    }

    public void mWrite(String child, Object value) {
        mDatabase.child(child).setValue(value);
    }

    public void mRemove(String child) {
        mDatabase.child(child).removeValue();
    }

    public Object mSearch(String child) {
        mDatabase.child(child).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object object = dataSnapshot.getValue(Object.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO: error handler
            }
        });
        return null;
    }

}
