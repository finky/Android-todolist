package com.r_mades.todolist.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.r_mades.todolist.data.TaskItem;

/**
 * Info about this file here.
 * Project: ToDoList
 * Created: veloc1
 * Date: 8/8/16
 */

public class SqliteTasksProvider extends Observable implements DatabaseProvider<TaskItem, Integer> {

    private static final String TABLE = "tasks";
    private SqliteDatabaseHelper mHelper;

    @Override
    public void init(Context context, int version) {
        mHelper = new SqliteDatabaseHelper(context, "tasks.db", version, new SqliteDatabaseHelper.SqliteDatabaseListener() {
            @Override
            public void onCreate(SQLiteDatabase database) {
                database.execSQL(String.format("CREATE TABLE %s (_id integer primary key autoincrement, title text, done integer);",
                    TABLE));
            }

            @Override
            public void onUpdate(SQLiteDatabase database, int oldVersion, int newVersion) {
                database.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE));
                onCreate(database);
            }
        });
    }

    @Override
    public void addObject(TaskItem object) {
        //        mHelper.getWritableDatabase().
        ContentValues cv = new ContentValues();

        cv.put("title", object.title);
        cv.put("done", object.done);

        if (getItem(object.id) != null) {
            mHelper.getWritableDatabase().update(TABLE, cv, "_id = ?", new String[]{"" + object.id});
        } else {
            mHelper.getWritableDatabase().insert(TABLE, null, cv);
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public void deleteObject(Integer id) {
        mHelper.getWritableDatabase().delete(TABLE, "_id = ?", new String[]{"" + id.intValue()});

        setChanged();
        notifyObservers();
    }

    @Override
    public void addList(Collection<TaskItem> collection) {
        for (TaskItem item : collection) {
            addObject(item);
        }

        setChanged();
        notifyObservers();
    }

    @Override
    public int count() {
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(String.format("SELECT count(*) from %s;", TABLE), null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    @Override
    public TaskItem getItem(Integer id) {
        TaskItem result = null;

        Cursor cursor = mHelper.getReadableDatabase().rawQuery(String.format("SELECT * from %s where _id = ?;", TABLE),
            new String[]{"" + id.intValue()});

        if (cursor.moveToFirst()) {
            result = getElementFromCursor(cursor);
        }

        cursor.close();

        return result;
    }

    private TaskItem getElementFromCursor(Cursor cursor) {
        TaskItem result = new TaskItem();

        result.id = cursor.getInt(cursor.getColumnIndex("_id"));
        result.title = cursor.getString(cursor.getColumnIndex("title"));
        result.done = cursor.getInt(cursor.getColumnIndex("done"));

        return result;
    }

    @Override
    public Collection<TaskItem> getAll() {
        ArrayList<TaskItem> result = new ArrayList<>();
        Cursor              cursor = mHelper.getReadableDatabase().rawQuery(String.format("SELECT * from %s", TABLE), null);

        boolean hasItems = cursor.moveToFirst();

        while (hasItems) {
            result.add(getElementFromCursor(cursor));
            hasItems = cursor.moveToNext();
        }

        cursor.close();

        return result;
    }

}
