package com.r_mades.todolist;

import android.app.Application;

import com.r_mades.todolist.data.TaskItem;
import com.r_mades.todolist.data.TaskItemRealm;
import com.r_mades.todolist.db.DatabaseProvider;
import com.r_mades.todolist.db.RealmTasksProvider;
import com.r_mades.todolist.db.SqliteTasksProvider;

/**
 * Info about this file here.
 * Project: ToDoList
 * Created: veloc1
 * Date: 8/8/16
 */

public class TodolistApp extends Application {

    private DatabaseProvider<TaskItemRealm, Integer> mProvider;

    @Override
    public void onCreate() {
        super.onCreate();

        mProvider = new RealmTasksProvider();
        mProvider.init(this, 6);
    }

    public DatabaseProvider<TaskItemRealm, Integer> getProvider() {
        return mProvider;
    }
}
