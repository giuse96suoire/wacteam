package com.dev.wacteam.taskmanager.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.dev.wacteam.taskmanager.R;


public class YesNoDialog {
    public static void mShow(Context context, String message, OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.confirm_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.yes_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onYes(dialog, which);
                    }
                })
                .setNegativeButton(R.string.no_dialog, new DialogInterface.OnClickListener() {
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
