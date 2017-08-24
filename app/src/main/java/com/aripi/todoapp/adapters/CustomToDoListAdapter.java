package com.aripi.todoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aripi.todoapp.R;
import com.aripi.todoapp.data.ToDoList;

import java.util.ArrayList;

/**
 * Created by saripirala on 8/21/17.
 */

public class CustomToDoListAdapter extends ArrayAdapter<ToDoList> {

    public CustomToDoListAdapter(Context context, ArrayList<ToDoList> lists) {
        super(context, 0, lists);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_todoitems, parent, false);
        }

        // Get the data item for this position
        ToDoList list = getItem(position);

        // Lookup view for data population
        TextView taskName = (TextView) convertView.findViewById(R.id.taskName);
        TextView taskPriority = (TextView) convertView.findViewById(R.id.taskPriority);
        // Populate the data into the template view using the data object
        taskName.setText(list.getTaskName());
        taskPriority.setText(list.getTaskPriority());
        // Return the completed view to render on screen
        return convertView;
    }


}
