package com.r_mades.todolist.adapters;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r_mades.todolist.R;
import com.r_mades.todolist.TodolistApp;
import com.r_mades.todolist.data.TaskItem;
import com.r_mades.todolist.db.DatabaseProvider;

/**
 * Info about this file here.
 * Project: ToDoList
 * Created: veloc1
 * Date: 8/8/16
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksViewHolder> implements Observer {

    private final Context                             mContext;
    private final DatabaseProvider<TaskItem, Integer> mProvider;
    private final OnDoneClickListener                 mClickListener;
    private       ArrayList<TaskItem>                 mData;

    public TasksAdapter(Context context, OnDoneClickListener listener) {
        super();
        mContext = context;
        mProvider = ((TodolistApp) context.getApplicationContext()).getProvider();
        //        mProvider = new OrmLiteProvider();
        //        mProvider = new GreenDaoProvider();

        mProvider.addObserver(this);

        refreshData();

        mClickListener = listener;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_task, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, final int position) {
        holder.title.setText(getItem(position).title);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onDoneClick(getItem(position));
            }
        });
    }

    private TaskItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void update(Observable observable, Object o) {
        refreshData();
    }

    private void refreshData() {
        mData = new ArrayList<>(mProvider.getAll());
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).done == 1){
                mData.remove(i);
                i--;
            }
        }
        notifyDataSetChanged();
    }

    public interface OnDoneClickListener {

        public void onDoneClick(TaskItem item);

    }
}
