package com.r_mades.todolist.db;

import java.util.Collection;
import java.util.Observer;

import android.content.Context;

import com.r_mades.todolist.data.TaskItem;

/**
 * Info about this file here.
 * Project: ToDoList
 * Created: veloc1
 * Date: 8/8/16
 */

public class OrmDatabaseHelper implements DatabaseProvider<TaskItem, Integer> {

    @Override
    public void init(Context context, int version) {
            
    }

    @Override
    public void addObject(TaskItem object) {

    }

    @Override
    public void deleteObject(Integer id) {

    }

    @Override
    public void addList(Collection<TaskItem> collection) {

    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public TaskItem getItem(Integer id) {
        return null;
    }

    @Override
    public Collection<TaskItem> getAll() {
        return null;
    }

    @Override
    public void addObserver(Observer observer) {

    }
}
