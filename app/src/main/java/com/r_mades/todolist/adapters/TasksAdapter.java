package com.r_mades.todolist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r_mades.todolist.R;
import com.r_mades.todolist.TodolistApp;
import com.r_mades.todolist.data.TaskItem;
import com.r_mades.todolist.db.DatabaseProvider;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Адаптер для управления задачами. Достает данные из базы данных, и складывает в RecyclerView.
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

        // заполняем адаптер начальными данными
        refreshData();

        mClickListener = listener;
    }

    /**
     * Создаем объект View для использования внутри списка. В R.layout.item_task лежит один элемент списка
     * @param parent родительский объект, обычно RecyclerView
     * @param viewType тип текущего элемента, в данном проетк не нужен
     * @return готовую вьюху, которая используется в onBindViewHolder
     */
    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_task, parent, false);
        return new TasksViewHolder(view);
    }

    /**
     * заполняем холдер нужными данными для каждого элемента
     * @param holder холдер для заполнения
     * @param position позиция текущего элемента
     */
    @Override
    public void onBindViewHolder(final TasksViewHolder holder, int position) {
        holder.title.setText(getItem(position).title);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onDoneClick(getItem(holder.getAdapterPosition()));
            }
        });
    }

    /**
     * Возвращаем элемент по позиции
     * @param position нужная позиция
     * @return элемент задачи из массива данных
     */
    private TaskItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * Обновляем данные, когда произошли изменения
     * @param observable источник изменений
     * @param o изменившиеся данные
     */
    @Override
    public void update(Observable observable, Object o) {
        refreshData();
    }

    /**
     * Достаем из базы данных все задачи, и удаляем те, которые уже выполнены.
     * Потом обновляем данные в списке
     */
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
