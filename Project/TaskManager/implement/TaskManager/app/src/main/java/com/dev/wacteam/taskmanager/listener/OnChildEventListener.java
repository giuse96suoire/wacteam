package com.dev.wacteam.taskmanager.listener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;



public interface OnChildEventListener {

    public void onChildChanged(DataSnapshot dataSnapshot, String s);

    public void onChildRemoved(DataSnapshot dataSnapshot);

    public void onChildMoved(DataSnapshot dataSnapshot, String s);

    public void onCancelled(DatabaseError databaseError);

    public void onChildAdded(DataSnapshot dataSnapshot, String s);
}
