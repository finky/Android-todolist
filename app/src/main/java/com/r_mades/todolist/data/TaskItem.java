package com.r_mades.todolist.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Info about this file here.
 * Project: ToDoList
 * Created: veloc1
 * Date: 8/8/16
 */
@DatabaseTable
public class TaskItem {

    @DatabaseField(generatedId = true)
    public int     id;
    @DatabaseField
    public String  title;
    @DatabaseField
    public int done;

}


/*
public class TaskItem {

    public int     id;
    public String  title;
    public int done;

}*/
