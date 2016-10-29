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
import com.dev.wacteam.taskmanager.model.Task;
import com.dev.wacteam.taskmanager.model.User;

import java.util.ArrayList;


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
        if (!newProject.getmTitle().equals(oldProject.getmTitle())) {
            message += " Title " + oldProject.getmTitle() + " -> " + newProject.getmTitle() + " \n";
        }
        message += notifyTaskChange(oldProject.getmTasks(), newProject.getmTasks());
        if (message.length() > 1) {
            System.out.println(" ============================> ");
            System.out.println(message + " ============================> message");
            System.out.println(" ============================> ");
            mNotify(mContext, newProject.getmTitle() + " - PROJECT HAS MANY CHANGE ", message);
        }
    }

    public static String notifyTaskChange(ArrayList<Task> oldTask, ArrayList<Task> newTask) {
        String message = "";
        if (oldTask != null && newTask != null) {
            int sizeOld = oldTask.size();
            int sizeNew = newTask.size();

                int sOld = 0, sNew = 0;
                do {
                    if (sOld < sizeOld) {
                        if (newTask.get(sNew).getmTaskId() == oldTask.get(sOld).getmTaskId()) {
                            message += notifyTaskChangeDetail(oldTask.get(sOld), newTask.get(sNew));
                            sNew++;
                            sOld++;
                        } else {
                            message += " Task title:" + oldTask.get(sOld).getmTitle() + " had been deleted \n";
                            sOld++;
                        }
                    } else {
                        message += " Task title:" + newTask.get(sNew).getmTitle() + " had been added \n";
                        sNew++;
                    }
                }
                while (sNew < sizeNew);
            }


        return message;

    }

    public static String notifyTaskChangeDetail(Task oldTask, Task newTask) {
        String message = "";
        if (newTask.getmTitle() != oldTask.getmTitle())
            message += " Task title:" + oldTask.getmTitle() + " title " + oldTask.getmTitle() + " % -> " + newTask.getmTitle() + " % \n";
        if (newTask.getmDeadline() != oldTask.getmDeadline())
            message += " Task title:" + newTask.getmTitle() + " deadline " + oldTask.getmDeadline() + " % -> " + newTask.getmDeadline() + " % \n";
        if (newTask.getmDescription() != oldTask.getmDescription())
            message += " Task title:" + newTask.getmTitle() + " description " + oldTask.getmDescription() + " % -> " + newTask.getmDescription() + " % \n";
//        message += notifyTaskChangeMember(oldTask.getmExecutors(), newTask.getmExecutors());
        return message;
    }

    public static String notifyTaskChangeMember(ArrayList<User> oldMember, ArrayList<User> newMember) {
        String message = "";
        int sizeOld = oldMember.size();
        int sizeNew = newMember.size();
        if (newMember.get(sizeOld - 1).getProfile().getUid() != oldMember.get(sizeNew - 1).getProfile().getUid()) {
            int sOld = 0, sNew = 0;
            do {
                if (sOld < sizeOld) {
                    if (newMember.get(sNew).getProfile().getUid() == oldMember.get(sOld).getProfile().getUid()) {
                        sNew++;
                        sOld++;
                    } else {
                        message += " Task member name:" + oldMember.get(sOld).getProfile().getDisplayName() + " had been removed \n";
                        sOld++;
                    }
                } else {
                    message += " Task member name:" + newMember.get(sNew).getProfile().getDisplayName() + " had been added \n";
                    sNew++;
                }
            }
            while (sNew < sizeNew);
        }
        return message;
    }
}
