package com.dev.wacteam.taskmanager.manager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.activity.MainActivity;
import com.dev.wacteam.taskmanager.model.Project;

/**
 * Created by giuse96suoire on 10/12/2016.
 */
public class NotificationsManager {

    private static void mNotify(Context mContext, String title, String content) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(true);
        if (SettingManager.isSound(mContext)) {
            mBuilder.setSound(alarmSound);
        }
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mContext, MainActivity.class);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    public static void notifyProjectChange(Project oldProject, Project newProject, Context mContext) {
        String message = "";
        if (newProject.getmComplete() != oldProject.getmComplete()) {
            message += " Complete " + oldProject.getmComplete() + "% -> " + newProject.getmComplete() + "% \n";
        }
        if (!newProject.getmDeadline().equals(oldProject.getmDeadline())) {
            message += " Deadline " + oldProject.getmDeadline() + " -> " + newProject.getmDeadline() + " \n";
        }
        if (!newProject.getmTitle().equals(oldProject.getmComplete())) {
            message += " Title " + oldProject.getmTitle() + " -> " + newProject.getmTitle() + " \n";
        }
        mNotify(mContext, newProject.getmTitle() + " - PROJECT HAS MANY CHANGE ", message);
    }
}
