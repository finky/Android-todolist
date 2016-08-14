package com.r_mades.todolist.data;

import com.r_mades.todolist.data.AutoIncrement.RealmAutoIncrement;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TaskItemRealm extends RealmObject {
    @PrimaryKey
    public int     id = RealmAutoIncrement.getInstance(this.getClass()).getNextIdFromModel();
    public String  title;
    public int done;
    public Date notifTime;
    public int notified;
}
