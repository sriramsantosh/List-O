package com.aripi.todoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.aripi.todoapp.R;
import com.aripi.todoapp.data.ToDoList;

import java.util.ArrayList;

/**
 * Created by saripirala on 8/21/17.
 */

public class CustomEditItemAdapter extends ArrayAdapter<ToDoList> {

    public CustomEditItemAdapter(Context context, ArrayList<ToDoList> lists) {
        super(context, 0, lists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_edit_items_2, parent, false);
        }

        // Get the data item for this position
        ToDoList list = getItem(position);

        // Lookup view for data population
        EditText taskName = (EditText) convertView.findViewById(R.id.taskName);
        EditText taskPriority = (EditText) convertView.findViewById(R.id.taskPriority);

        // Populate the data into the template view using the data object
        taskName.setText(list.getTaskName());
        taskPriority.setText(list.getTaskPriority());
        // Return the completed view to render on screen
        return convertView;
    }

}
