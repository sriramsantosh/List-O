package com.aripi.todoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.aripi.todoapp.R;
import com.aripi.todoapp.data.TodoItemsDbHelper;

/**
 * Created by saripirala on 8/23/17.
 */

public class AddNewItemActivity extends AppCompatActivity {

    private TodoItemsDbHelper dbHelper;
    private EditText taskNameEt;
    private Spinner taskPriorityEt;
    private Spinner taskStatusEt;
    private EditText taskDueDateEt;
    private EditText taskNotesEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        dbHelper = TodoItemsDbHelper.getInstance(this);

        taskNameEt = (EditText) findViewById(R.id.taskName);
        taskPriorityEt = (Spinner) findViewById(R.id.taskPriority);
        taskStatusEt = (Spinner) findViewById(R.id.taskStatus);

        taskNotesEt = (EditText) findViewById(R.id.taskNotes);
        taskDueDateEt = (EditText)findViewById(R.id.dueDate);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(item.getTitle() !=null && item.getTitle().toString().equalsIgnoreCase("Save")){

            dbHelper.insertToDoItems(taskNameEt.getText().toString(),taskDueDateEt.getText().toString(), taskNotesEt.getText().toString(),
                                     taskPriorityEt.getSelectedItem().toString(), taskStatusEt.getSelectedItem().toString(), 4343 );


        }

        Intent data = new Intent();
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent

        return super.onOptionsItemSelected(item);
    }


}
