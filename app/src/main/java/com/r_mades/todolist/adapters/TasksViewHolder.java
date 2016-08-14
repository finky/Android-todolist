package com.r_mades.todolist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.r_mades.todolist.R;

/**
 * Info about this file here.
 * Project: ToDoList
 * Created: veloc1
 * Date: 8/8/16
 */

public class TasksViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    EditText editTitle;
    ImageButton editDone;
    View button;

    public TasksViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.title);
        button = itemView.findViewById(R.id.done);
        editTitle = (EditText) itemView.findViewById(R.id.title_edit);
        editDone = (ImageButton) itemView.findViewById(R.id.edit_finished);
    }
}
