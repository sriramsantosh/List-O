package com.aripi.todoapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aripi.todoapp.R;
import com.aripi.todoapp.data.Constants;
import com.aripi.todoapp.data.ToDoList;
import com.aripi.todoapp.data.TodoItemsDbHelper;
import com.aripi.todoapp.adapters.CustomEditItemAdapter;
import com.aripi.todoapp.activities.MainActivity;


/**
 * Created by saripirala on 8/23/17.
 */

public class EditItemDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {


    private static String taskName;
    private TodoItemsDbHelper dbHelper;
    private CustomEditItemAdapter customAdapter;
    private  ListView listView;
    private ArrayAdapter<String> aToDoAdapter;
    private EditText taskNameET;
    private Spinner taskPriorityET;
    private EditText taskNotesET;
    private Spinner taskStatusET;
    private EditText taskDueDateET;
    private Button saveButton;
    private Button cancelButton ;

    public EditItemDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }


    public static EditItemDialogFragment newInstance(String title, String task) {
        EditItemDialogFragment frag = new EditItemDialogFragment();
        Bundle args = new Bundle();
        args.putString("Title", title);
        frag.setArguments(args);
        taskName = task;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_edit_items_3, container);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = TodoItemsDbHelper.getInstance(getContext());

        ToDoList toDoList = dbHelper.getItem(taskName);

        // Get field from view
        taskNameET = (EditText) view.findViewById(R.id.editTaskName);
        taskPriorityET = (Spinner) view.findViewById(R.id.editTaskPriority);
        taskNotesET = (EditText) view.findViewById(R.id.editTaskNotes);
        taskStatusET = (Spinner) view.findViewById(R.id.editTaskStatus);
        taskDueDateET = (EditText) view.findViewById(R.id.editDueDate);
        saveButton = (Button)view.findViewById(R.id.saveButton);
        cancelButton = (Button)view.findViewById(R.id.cancelButton);
        saveButton.setOnClickListener(saveButtonListener);
        cancelButton.setOnClickListener(cancelButtonListener);

        taskNameET.setText(toDoList.getTaskName());
        taskPriorityET.setSelection(getPriorityInfo(toDoList.getPriority()));
        taskNotesET.setText(toDoList.getTaskNotes());
        taskStatusET.setSelection(getStatusInfo(toDoList.getStatus()));
        taskDueDateET.setText(toDoList.getDueDate());

        taskNameET.setEnabled(false);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Edit Item");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        //      mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }


    private View.OnClickListener saveButtonListener = new View.OnClickListener()
    {

        public void onClick(View v)
        {
            dbHelper.updateToDoItem(taskNameET.getText().toString(), taskDueDateET.getText().toString(), taskNotesET.getText().toString(), taskPriorityET.getSelectedItem().toString(), taskStatusET.getSelectedItem().toString(), 12121 );
            dismiss();

            Toast.makeText(getContext(), "Details saved in the DB", Toast.LENGTH_SHORT).show();
            refreshMainAct();
        }
    };

    private  void refreshMainAct()
    {
        Intent refresh = new Intent(getActivity(), MainActivity.class);
        startActivity(refresh);
    }

    private View.OnClickListener cancelButtonListener = new View.OnClickListener()
    {

        public void onClick(View v)
        {
            dismiss();
        }

    };

    // 1. Defines the listener interface with a method passing back data result.
    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }

    private int getStatusInfo(String taskStatus)
    {
        if(taskStatus.equalsIgnoreCase(Constants.STATUS_TODO))
            return 0;
        else if(taskStatus.equalsIgnoreCase(Constants.STATUS_IN_PROGRESS))
            return 1;

        else if(taskStatus.equalsIgnoreCase(Constants.STATUS_DONE))
            return 2;

        return 0;
    }

    private int getPriorityInfo(String taskPriority)
    {
        if(taskPriority.equalsIgnoreCase(Constants.PRIORITY_HIGH))
            return 0;
        else if(taskPriority.equalsIgnoreCase(Constants.PRIORITY_MEDIUM))
            return 1;

        else if(taskPriority.equalsIgnoreCase(Constants.PRIORITY_LOW))
            return 2;

        return 0;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }
}
