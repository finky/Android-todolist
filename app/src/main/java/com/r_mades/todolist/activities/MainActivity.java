package com.r_mades.todolist.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.r_mades.todolist.R;
import com.r_mades.todolist.fragments.TodoMainFragment;

public class MainActivity extends Activity {

    public static final int CONTAINER_ID = R.id.container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startFragment(new TodoMainFragment(), false);
    }

    /**
     * Базовый метод для запуска фрагмента
     *
     * @param fragment новый фрагмент
     */
    public void startFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(CONTAINER_ID, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
