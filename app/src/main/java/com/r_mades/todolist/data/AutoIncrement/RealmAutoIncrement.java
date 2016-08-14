package com.r_mades.todolist.data.AutoIncrement;

import com.r_mades.todolist.data.TaskItemRealm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Implementation of Auto Increment feature for Realm
 * RealmAutoIncrement is a singleton which maintain the last id saved from each database model.
 *
 * To get next id from anyone model, simple call the method getNextIdFromModel.
 * @see #getNextIdFromModel(Class)
 */
public final class RealmAutoIncrement {

    private Map<Class<? extends RealmObject>, AtomicInteger> modelMap = new HashMap<>();
    private static RealmAutoIncrement autoIncrementMap;
    private Class<? extends RealmObject> mObj;

    private RealmAutoIncrement(Class<? extends RealmObject> obj) {
        mObj = obj;
        modelMap.put(obj, new AtomicInteger(getLastIdFromModel(mObj)));
    }

    private int getLastIdFromModel(Class<? extends RealmObject> clazz) {

        String primaryKeyColumnName = "id";
        Number lastId = Realm.getDefaultInstance().where(clazz).max(primaryKeyColumnName);
        return lastId == null ? 0 : lastId.intValue();
    }

    public Integer getNextIdFromModel() {

        if (isValidMethodCall()) {

            AtomicInteger modelId = modelMap.get(mObj);

            if (modelId == null) {
                return 0;
            }
            return modelId.incrementAndGet();
        }
        return 0;
    }

    private boolean isValidMethodCall() {

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        for (StackTraceElement stackTraceElement : stackTraceElements) {

            if (stackTraceElement.getMethodName().equals("newInstance")) {
                return false;
            }
        }
        return true;
    }

    public static RealmAutoIncrement getInstance(Class<? extends RealmObject> obj) {

        if (autoIncrementMap == null) {
            autoIncrementMap = new RealmAutoIncrement(obj);
        }
        return autoIncrementMap;
    }
}
