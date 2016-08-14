package com.r_mades.todolist.data;

import com.j256.ormlite.field.DatabaseField;
import com.r_mades.todolist.data.AutoIncrement.RealmAutoIncrement;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class TaskItemRealm extends RealmObject {
    @PrimaryKey
    public int     id = RealmAutoIncrement.getInstance(this.getClass()).getNextIdFromModel();
    public String  title;
    public int done;
}
