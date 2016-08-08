package com.r_mades.todolist.db;

import java.util.Collection;
import java.util.Observer;

import android.content.Context;

/**
 * Info about this file here.
 * Project: ToDoList
 * Created: veloc1
 * Date: 8/8/16
 */

public interface DatabaseProvider<ITEM_TYPE, ID_TYPE>{

    public void init(Context context, int version);

    public void addObject(ITEM_TYPE object);

    public void deleteObject(ID_TYPE id);

    public void addList(Collection<ITEM_TYPE> collection);

    public int count();

    public ITEM_TYPE getItem(ID_TYPE id);

    public Collection<ITEM_TYPE> getAll();

    public void addObserver(Observer observer);
}
