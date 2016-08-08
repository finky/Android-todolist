package com.r_mades.todolist.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class OrmLiteDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static String DATABASE_NAME = "database.db";

    private Class<?>[] mClasses;

    public OrmLiteDatabaseHelper(Context context, int databaseVersion, Class<?>[] classes) {
        this(context, DATABASE_NAME, databaseVersion, classes);
    }

    public OrmLiteDatabaseHelper(Context context, String databaseName, int databaseVersion, Class<?>[] classes) {
        super(context, databaseName == null ? DATABASE_NAME : databaseName, null, databaseVersion);
        mClasses = classes.clone();
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connection) {
        for (Class<?> clazz : mClasses) {
            try {
                TableUtils.createTable(connection, clazz);
            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          ConnectionSource connection,
                          int oldVersion,
                          int newVersion) {
        for (Class<?> clazz : mClasses) {
            try {
                TableUtils.dropTable(connection, clazz, true);
            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
        onCreate(db, connection);
    }

    /**
     * Удалить базу данных.
     */
    public void dropBase() {
        for (Class<?> clazz : mClasses) {
            try {
                TableUtils.dropTable(getConnectionSource(), clazz, true);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        onCreate(getWritableDatabase(), getConnectionSource());
    }
}
