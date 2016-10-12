package com.dev.wacteam.taskmanager.database;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by giuse96suoire on 10/12/2016.
 */
public class DatabaseManager {
    private Context mContext;

    public DatabaseManager(Context context) {
        this.mContext = context;
    }

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public void mWrite(String child, Object value) {
        mDatabase.child(child).setValue(value);
    }

    public void mDelete(String child) {
        mDatabase.child(child).removeValue();
        mDatabase.child(child).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(mContext,"Delete complete", Toast.LENGTH_LONG).show();
            }
        });
    }
}
