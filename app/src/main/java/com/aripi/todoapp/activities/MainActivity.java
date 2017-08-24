package com.aripi.todoapp.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.Toast;

import com.aripi.todoapp.adapters.CustomToDoListAdapter;
import com.aripi.todoapp.fragments.EditItemDialogFragment;
import com.aripi.todoapp.R;
import com.aripi.todoapp.data.ToDoList;
import com.aripi.todoapp.data.TodoItemsDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.EditNameDialogListener {

    private CustomToDoListAdapter customAdapter;
    private ListView lvItems;
    private final int REQUEST_CODE = 11;
    private TodoItemsDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        dbHelper = TodoItemsDbHelper.getInstance(this);

        populateToDoList();

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                ToDoList toDoList = (ToDoList)lvItems.getItemAtPosition(i);
                dbHelper.deleteToDoItem(toDoList.getTaskName());
                Toast.makeText(getApplicationContext(), "Task successfully removed", Toast.LENGTH_SHORT).show();

                populateToDoList();
                return true;
            }
        });

        lvItems.setOnItemClickListener( new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToDoList toDoList = (ToDoList)lvItems.getItemAtPosition(i);
                showEditDialog(toDoList.getTaskName());
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.all_tasks_menu, menu);

        return true;
    }

    public void populateToDoList() {

        ArrayList<ToDoList> arrayOfItems = dbHelper.getCurrentToDoItems();
        // Create the adapter to convert the array to views
        customAdapter = new CustomToDoListAdapter(this, arrayOfItems);
        // Attach the adapter to a ListView
        lvItems.setAdapter(customAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent = new Intent(MainActivity.this, AddNewItemActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            populateToDoList();
        }
    }

    private void showEditDialog(String taskName) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialogFragment editNameDialogFragment = EditItemDialogFragment.newInstance("Edit Item", taskName);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        populateToDoList();
    }
}
