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
public class DatabaseManager {
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

//    private Context mContext;
//
//    public DatabaseManager(Context context) {
//        this.mContext = context;
//    }

    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static void mWrite(String child, Object value) {
        mDatabase.child(child).setValue(value);
    }

//    public void mDelete(String child) {
//        mDatabase.child(child).removeValue();
//        mDatabase.child(child).removeValue(new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                Toast.makeText(mContext, "Delete complete", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}
