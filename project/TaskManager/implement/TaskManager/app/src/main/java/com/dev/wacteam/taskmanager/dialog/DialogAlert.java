package com.dev.wacteam.taskmanager.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.dev.wacteam.taskmanager.R;

public class DialogAlert {
    public static void mShow(Context context, String message){
        new AlertDialog.Builder(context)
                .setTitle(R.string.Error_Dialog)
                .setMessage(message)
                .setPositiveButton(R.string.oke_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
