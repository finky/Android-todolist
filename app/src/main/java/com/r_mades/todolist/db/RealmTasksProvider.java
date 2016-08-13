package com.r_mades.todolist.db;

import android.content.Context;
import android.support.annotation.Nullable;

import com.r_mades.todolist.data.TaskItemRealm;

import java.util.Collection;
import java.util.Observable;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class RealmTasksProvider extends Observable implements DatabaseProvider<TaskItemRealm, Integer> {


    @Override
    public void init(Context context, int version) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @Override
    public void addObject(final TaskItemRealm object) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(object);
            }
        });
    }

    @Override
    public void deleteObject(Integer id) {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<TaskItemRealm> results = realm.where(TaskItemRealm.class)
                .equalTo("id", id)
                .findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    @Override
    public void addList(final Collection<TaskItemRealm> collection) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyFromRealm(collection);
            }
        });
    }

    @Override
    public int count() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<TaskItemRealm> results = realm.where(TaskItemRealm.class)
                .findAll();
        return results.size();
    }

    @Override
    @Nullable
    public TaskItemRealm getItem(Integer id) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(TaskItemRealm.class)
                .equalTo("id", id)
                .findFirst();
    }

    @Override
    public Collection<TaskItemRealm> getAll() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(TaskItemRealm.class)
                .findAll();
    }
}
