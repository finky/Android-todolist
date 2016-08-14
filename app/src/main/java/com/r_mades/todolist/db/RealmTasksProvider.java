package com.r_mades.todolist.db;

import android.content.Context;
import android.support.annotation.Nullable;

import com.r_mades.todolist.data.TaskItem;
import com.r_mades.todolist.data.TaskItemRealm;

import java.util.Collection;
import java.util.Observable;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static android.R.attr.id;


public class RealmTasksProvider extends Observable implements DatabaseProvider<TaskItemRealm, Integer> {

    Realm mRealm;

    @Override
    public void init(Context context, int version) {
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder(context)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @Override
    public void addObject(final TaskItemRealm object) {
        mRealm = Realm.getDefaultInstance();
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(object);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                setChanged();
                notifyObservers();
            }
        });
        mRealm.close();
    }

    @Override
    public void deleteObject(Integer id) {
        mRealm = Realm.getDefaultInstance();
        final RealmResults<TaskItemRealm> results = mRealm.where(TaskItemRealm.class)
                .equalTo("id", id)
                .findAll();
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                setChanged();
                notifyObservers();
            }
        });
        mRealm.close();
    }

    @Override
    public void addList(final Collection<TaskItemRealm> collection) {
        mRealm = Realm.getDefaultInstance();
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(collection);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                setChanged();
                notifyObservers();
            }
        });
        mRealm.close();
    }

    @Override
    public int count() {
        mRealm = Realm.getDefaultInstance();
        RealmResults<TaskItemRealm> results = mRealm.where(TaskItemRealm.class)
                .findAll();
        mRealm.close();
        return results.size();
    }

    @Override
    @Nullable
    public TaskItemRealm getItem(Integer id) {
        mRealm = Realm.getDefaultInstance();
        TaskItemRealm realmItem = mRealm.where(TaskItemRealm.class)
                .equalTo("id", id)
                .findFirst();
        TaskItemRealm item = mRealm.copyFromRealm(realmItem);
        mRealm.close();
        return item;
    }

    @Override
    public Collection<TaskItemRealm> getAll() {
        mRealm = Realm.getDefaultInstance();
        Collection<TaskItemRealm> managedRealmCollection = mRealm.where(TaskItemRealm.class)
                .findAll();
        Collection<TaskItemRealm> itemsCollection = mRealm.copyFromRealm(managedRealmCollection);
        mRealm.close();
        return itemsCollection;
    }


    public void addObjectFromNonLooperThread(final TaskItemRealm object) {
        mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(object);
            }
        });
        mRealm.close();
    }
}
