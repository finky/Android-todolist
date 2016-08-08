package com.r_mades.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDatabaseHelper extends SQLiteOpenHelper {

    private final SqliteDatabaseListener mListener;

    public SqliteDatabaseHelper(Context context, String name, int version, SqliteDatabaseListener listener) {
        super(context, name, null, version);
        mListener = listener;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        mListener.onCreate(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        mListener.onUpdate(database, oldVersion, newVersion);
    }

    interface SqliteDatabaseListener {

        void onCreate(SQLiteDatabase database);

        void onUpdate(SQLiteDatabase database, int oldVersion, int newVersion);
    }
}