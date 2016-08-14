package com.r_mades.todolist.notificationservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;

public class TimeService extends Service {

    public static final long NOTIFY_INTERVAL = 10 * 1000;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;

    @Override
    public void onCreate() {
        // cancel if already existed
        System.out.println("service started");
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimerTaskNotification(this), 0, NOTIFY_INTERVAL);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
