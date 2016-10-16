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
public class RemoteDBManager {


    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static boolean mIsExistChild(String child) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(child);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null || user.getDisplayName() == null || user.getDob() == null) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return false;
    }

    public static void mWrite(String child, Object value) {
        mDatabase.child(child).setValue(value);
    }

    public static void mRemove(String child) {
        mDatabase.child(child).removeValue();
    }


}
