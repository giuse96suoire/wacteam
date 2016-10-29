package com.dev.wacteam.taskmanager.database;

/**
 * Created by Ngoc on 10/29/2016.
 */

public class FirebaseDatabase {
    private static com.google.firebase.database.FirebaseDatabase mDatabase;

    public static com.google.firebase.database.FirebaseDatabase getInstance() {
        if (mDatabase == null) {
            mDatabase = com.google.firebase.database.FirebaseDatabase.getInstance();
            try{
                mDatabase.setPersistenceEnabled(true);

            }catch (Exception e){
                System.out.println("PERSISTENCE IS ENABLED! DON'T SET AGAIN!");
            }
        }
        return mDatabase;
    }
}
