package com.r_mades.todolist;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import com.r_mades.todolist.data.TaskItem;

/**
 * Project: Android-todolist
 * Created: Vendetta
 * Date: 08.08.2016
 */

public class NotifService extends IntentService {
    public NotifService() {
        super("NotifService");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onHandleIntent(Intent intent) {
        TaskItem item  =
                ((TodolistApp) getApplication()).getProvider().getItem(intent.getIntExtra("message", 0));
        int      delay = intent.getIntExtra("delay", 0);

        //        new NotifAsyncTask(item, delay).execute();
        try {
            Thread.sleep(delay * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //            Intent intent1 = new Intent(this, TodoMainFragment.class);
        //            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification builder = new Notification.Builder(this)
                .setTicker("Задача!1")
                .setContentTitle("Прошло " + delay + " !")
                .setContentText(item.title)
                .setSmallIcon(R.mipmap.ic_launcher)
//                .addAction(R.mipmap.ic_launcher, "Завершить", pIntent)
                .build();

        builder.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, builder);

    }

    class NotifAsyncTask extends AsyncTask<Void, Void, Void> {

        int      mDelay;
        TaskItem mItem;

        public NotifAsyncTask(TaskItem item, int delay) {
            mItem = item;
            mDelay = delay;
        }

        @Override
        protected Void doInBackground(Void... params) {
//            NotificationManager notificationManager =
//                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            //            Intent intent1 = new Intent(this, TodoMainFragment.class);
//            //            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//            Notification builder = new Notification.Builder(this)
//                    .setTicker("Задача!1")
//                    .setContentTitle("Прошло " + intent.getIntExtra("delay") + " !")
//                    .setContentText(mItem.title)
//                    .setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pIntent)
//                    .addAction(R.mipmap.ic_launcher, "Завершить", pIntent)
//                    .build();
//
//            builder.flags |= Notification.FLAG_AUTO_CANCEL;
//
//            notificationManager.notify(0, builder);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
