package com.r_mades.todolist.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.concurrent.Callable;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.r_mades.todolist.data.TaskItem;

/**
 * Info about this file here.
 * Project: ToDoList
 * Created: veloc1
 * Date: 8/8/16
 */

public class OrmLiteTasksProvider extends Observable implements DatabaseProvider<TaskItem, Integer> {

    private OrmLiteDatabaseHelper  mHelper;
    private Class<TaskItem>        mModelClass;
    private Dao<TaskItem, Integer> mDao;

    @Override
    public void init(Context context, int version) {
        mHelper = new OrmLiteDatabaseHelper(context, "tasks_ormlite.db", version, new Class[]{TaskItem.class});
        mModelClass = TaskItem.class;
        try {
            mDao = mHelper.getDao(mModelClass);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void addObject(TaskItem object) {
        if (object == null) {
            return;
        }

        try {
            mDao.createOrUpdate(object);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        setChanged();
        notifyObservers();
    }

    @Override
    public void deleteObject(Integer id) {
        try {
            mDao.deleteById(id);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        notifyObservers();
    }

    @Override
    public void addList(final Collection<TaskItem> collection) {
        if (collection == null || collection.size() == 0) {
            return;
        }

        try {
            mDao.callBatchTasks(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    for (TaskItem item : collection) {
                        mDao.createOrUpdate(item);
                    }
                    return null;
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public int count() {
        try {
            return (int) mDao.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public TaskItem getItem(Integer id) {
        try {
            return mDao.queryForId(id);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<TaskItem> getAll() {
        try {
            return mDao.queryForAll();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return new ArrayList<>();
    }

}
