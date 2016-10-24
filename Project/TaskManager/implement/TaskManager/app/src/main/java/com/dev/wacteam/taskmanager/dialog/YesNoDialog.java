package com.dev.wacteam.taskmanager.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by huynh.mh on 10/24/2016.
 */
public class YesNoDialog {
    public static void mShow(Context context, String message, OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm")
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onYes(dialog, which);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onNo(dialog, which);
                    }
                })
                .show();
    }

    public interface OnClickListener {
        public void onYes(DialogInterface dialog, int which);

        public void onNo(DialogInterface dialog, int which);
    }

    ;
}
