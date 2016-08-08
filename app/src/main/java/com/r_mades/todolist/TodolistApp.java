package com.r_mades.todolist;

import android.app.Application;

import com.r_mades.todolist.data.TaskItem;
import com.r_mades.todolist.db.DatabaseProvider;
import com.r_mades.todolist.db.SqliteTasksProvider;

/**
 * Info about this file here.
 * Project: ToDoList
 * Created: veloc1
 * Date: 8/8/16
 */

public class TodolistApp extends Application {

    private DatabaseProvider<TaskItem, Integer> mProvider;

    @Override
    public void onCreate() {
        super.onCreate();

        mProvider = new SqliteTasksProvider();
        mProvider.init(this, 5);
    }

    public DatabaseProvider<TaskItem, Integer> getProvider(){
        return mProvider;
    }
}
