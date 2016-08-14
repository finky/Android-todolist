package com.r_mades.todolist.notificationservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

import com.r_mades.todolist.R;
import com.r_mades.todolist.TodolistApp;
import com.r_mades.todolist.activities.MainActivity;
import com.r_mades.todolist.data.TaskItemRealm;
import com.r_mades.todolist.db.DatabaseProvider;
import com.r_mades.todolist.db.RealmTasksProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;
import java.util.TimerTask;

public class TimerTaskNotification extends TimerTask {
    private Context mContext;
    RealmTasksProvider mProvider = new RealmTasksProvider();

    public TimerTaskNotification(Context context) {
        this.mContext = context;
        mProvider.init(context, 0);
    }

    @Override
    public void run() {
        RealmTasksProvider realmProvider = new RealmTasksProvider();
        realmProvider.init(mContext, 0);
        ArrayList<TaskItemRealm> data = new ArrayList<>(realmProvider.getAll());
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        for (final TaskItemRealm item : data) {
            if (item.notifTime != null)
                if (c.getTime().after(item.notifTime) && item.notified == 0 && item.done == 0) {
                    makeNotification(item.title);
                    item.notified = 1;
                    realmProvider.addObjectFromNonLooperThread(item);
                }
        }

    }

    private void makeNotification(String description) {
        NotificationManager notificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(mContext, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle("Due soon..")
                        .setContentText(description)
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true);


        Random random = new Random();
        int notificationId = random.nextInt(9999 - 1000) + 1000;

        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
