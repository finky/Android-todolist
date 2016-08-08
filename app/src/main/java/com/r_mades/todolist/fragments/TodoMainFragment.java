package com.r_mades.todolist.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.r_mades.todolist.R;
import com.r_mades.todolist.TodolistApp;
import com.r_mades.todolist.adapters.TasksAdapter;
import com.r_mades.todolist.data.TaskItem;

/**
 * Info about this file here.
 * Project: ToDoList
 * Created: veloc1
 * Date: 8/8/16
 */

public class TodoMainFragment extends Fragment implements View.OnClickListener {

    EditText mNewTaskText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new TasksAdapter(getActivity(), new TasksAdapter.OnDoneClickListener() {
            @Override
            public void onDoneClick(TaskItem item) {
                item.done = 1;
                ((TodolistApp) getActivity().getApplication()).getProvider().addObject(item);
            }
        }));

        mNewTaskText = (EditText) root.findViewById(R.id.new_task);
        View addButton = root.findViewById(R.id.add_button);

        addButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        TaskItem item = new TaskItem();
        item.title = mNewTaskText.getText().toString();
        ((TodolistApp) getActivity().getApplication()).getProvider().addObject(item);

        mNewTaskText.setText("");
    }
}
