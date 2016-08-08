package com.r_mades.todolist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.r_mades.todolist.data.TaskItem;
import com.r_mades.todolist.db.DatabaseProvider;
import com.r_mades.todolist.db.SqliteTasksProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Info about this file here.
 * Project: ToDoList
 * Created: veloc1
 * Date: 8/8/16
 */
@RunWith(AndroidJUnit4.class)
public class TodolistTest {

    @Test
    public void testSqliteDatabase() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseProvider<TaskItem, Integer> provider = new SqliteTasksProvider();
        provider.init(appContext, 2);

        TaskItem task = new TaskItem();
        task.title = "My super task";
        provider.addObject(task);

        ArrayList<TaskItem> databaseItems = new ArrayList<>(provider.getAll());
        TaskItem            lastObject    = databaseItems.get(databaseItems.size() - 1);
        assertEquals(lastObject.title, task.title);

        int lastObjectId = lastObject.id;
        lastObject.title += " " + lastObject.id;
        provider.addObject(lastObject);

        databaseItems = new ArrayList<>(provider.getAll());
        lastObject = databaseItems.get(databaseItems.size() - 1);

        assertEquals(lastObject.id, lastObjectId + 1);

        assertTrue(provider.count() > 0);
    }

}
