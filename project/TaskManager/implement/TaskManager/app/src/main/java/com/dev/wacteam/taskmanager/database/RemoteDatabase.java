package com.dev.wacteam.taskmanager.database;

import com.dev.wacteam.taskmanager.listener.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RemoteDatabase {


    private DatabaseReference mDatabase;

    public RemoteDatabase() {
        mDatabase = com.dev.wacteam.taskmanager.database.FirebaseDatabase.getInstance().getReference();
    }

    public void mReadDataOnce(String child, OnGetDataListener listener) {
        listener.onStart();
        mDatabase.child(child).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }


    public void mReadData(String child, OnGetDataListener listener) {
        mDatabase.child(child).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void mWriteIfNotExist(String child, Object value) {
        mDatabase.child(child).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object object = dataSnapshot.getValue(Object.class);
                if (object == null) {
                    mWrite(child, value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO: error handler
                System.out.println(databaseError.getMessage() + " ERROR ---------------------->");
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
