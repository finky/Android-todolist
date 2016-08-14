package com.r_mades.todolist.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.r_mades.todolist.NotifService;
import com.r_mades.todolist.R;
import com.r_mades.todolist.TodolistApp;
import com.r_mades.todolist.adapters.TasksAdapter;
import com.r_mades.todolist.data.TaskItem;
import com.r_mades.todolist.data.TaskItemRealm;

import java.util.Calendar;
import android.app.DatePickerDialog.OnDateSetListener;

import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.Realm;

import static android.R.attr.delay;

/**
 * Info about this file here.
 * Project: ToDoList
 * Created: veloc1
 * Date: 8/8/16
 */

public class TodoMainFragment extends Fragment implements View.OnClickListener {

    EditText mNewTaskText;

    private TaskItemRealm mTaskItem = new TaskItemRealm();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Достаем разметку из xml-файла и создаем из нее объект view для использования
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        // Находим recyclerView используя его id в разметке
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Ставим наш адаптер для recyclerView, чтобы он мог показать нужные элементы, в конструктор отдаем слушатель нажатий на кнопку "Завершить"
        recyclerView.setAdapter(new TasksAdapter(getActivity(), new TasksAdapter.OnDoneClickListener() {
            @Override
            public void onDoneClick(TaskItemRealm item) {
                // Обновляем элемент
                item.done = 1;
                ((TodolistApp) getActivity().getApplication()).getProvider().addObject(item);
            }
        }));

        mNewTaskText = (EditText) root.findViewById(R.id.new_task);
        mNewTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean processed = true;
                if (s instanceof Spannable) {
                    BackgroundColorSpan[] spans = s.getSpans(0, s.length(), BackgroundColorSpan.class);
                    if (spans == null || spans.length == 0) {
                        processed = false;
                    }
                } else {
                    processed = false;
                }
                if (s.toString().matches("(.*) (\\d*) сек (.*)?") && !processed) {
                    String text    = s.toString();
                    int    seconds = s.toString().indexOf(" сек ");

                    int secondsStart = text.substring(0, seconds).lastIndexOf(' ');

                    int secondsVal = Integer.valueOf(text.substring(secondsStart + 1, seconds));

                    Spannable spannable = new SpannableString(s);

                    spannable.setSpan(new BackgroundColorSpan(0x80000000), secondsStart + 1, seconds + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    mNewTaskText.setText(spannable);
                    mNewTaskText.setSelection(mNewTaskText.getText().length() - 1);
                }
            }
        });

        View addButton = root.findViewById(R.id.add_button);

        addButton.setOnClickListener(this);

        ImageButton notifyButton = (ImageButton) root.findViewById(R.id.notify_date);
        notifyButton.setOnClickListener(this);

        return root;
    }

    /**
     * Описываем, что происходит по клику на кнопку.
     * @param view на какую вьюху произошел клик
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_button:
                mTaskItem.title = mNewTaskText.getText().toString();
                ((TodolistApp) getActivity().getApplication()).getProvider().addObject(mTaskItem);

                int delay = findDelayInString(mTaskItem.title);
                if (delay != -1) {
                    Intent intent = new Intent(getActivity(), NotifService.class);
                    intent.putExtra(NotifService.ID, ((TodolistApp) getActivity().getApplicationContext()).getProvider().count());
                    intent.putExtra(NotifService.DELAY, delay);
                    getActivity().startService(intent);
                }

                mNewTaskText.setText("");
                mTaskItem = new TaskItemRealm();
                break;
            case R.id.notify_date:
                int year, month , day;
                Calendar c = Calendar.getInstance();
                if (mTaskItem.notifTime != null)
                    c.setTime(mTaskItem.notifTime);
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getActivity(), myCallBack, year, month, day).show();
                break;
        }
    }

    OnDateSetListener myCallBack = new OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);
            mTaskItem.notifTime = c.getTime();
        }
    };



    private int findDelayInString(String text) {
        if (text.matches("(.*) (\\d*) сек (.*)?")) {
            int seconds = text.toString().indexOf(" сек ");

            int secondsStart = text.substring(0, seconds).lastIndexOf(' ');

            int secondsVal = Integer.valueOf(text.substring(secondsStart + 1, seconds));

            return secondsVal;
        }
        return -1;
    }
}
