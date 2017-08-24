package com.aripi.todoapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by saripirala on 8/14/17.
 */

public class TodoItemsDbHelper extends SQLiteOpenHelper{

    private static TodoItemsDbHelper sInstance;

    public static synchronized TodoItemsDbHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new TodoItemsDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private TodoItemsDbHelper(Context context) {
        super(context, "items_db", null, 1);
    }

//    public TodoItemsDbHelper( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, "items_db", null, 1);
//    }

    @Override
    public void onCreate( SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ITEMS(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK_NAME TEXT UNIQUE, DUE_DATE TEXT, TASK_NOTES TEXT, PRIORITY TEXT, STATUS TEXT, POSITION INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no impl
        db.execSQL("DROP TABLE IF EXISTS items_db;");
        onCreate(db);
    }

    public void insertToDoItems(String taskName, String dueDate, String taskNotes, String priority, String status, int position)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put("TASK_NAME", taskName);
        contentValues.put("DUE_DATE", dueDate);
        contentValues.put("TASK_NOTES", taskNotes);
        contentValues.put("PRIORITY", priority);
        contentValues.put("STATUS", status);
        contentValues.put("POSITION", position);

        this.getWritableDatabase().insertOrThrow("ITEMS", "", contentValues);
    }


    public void deleteToDoItem(String taskName)
    {
        if(taskName==null)
            return;
        String where = "TASK_NAME" + "='" + taskName + "'";
        this.getWritableDatabase().delete("ITEMS",where, null );
    }

    public void updateToDoItem(String taskName, String dueDate, String taskNotes, String priority, String status, int position)
    {
        this.getWritableDatabase().execSQL("UPDATE ITEMS SET DUE_DATE='" +dueDate +
                                           "', TASK_NOTES='" + taskNotes +
                                           "', PRIORITY='" + priority +
                                           "', STATUS='" + status + "' WHERE TASK_NAME='" + taskName + "'");

    }

    public ArrayList<ToDoList> getCurrentToDoItems()
    {
        ArrayList<ToDoList> toDoItems = new ArrayList<ToDoList>();

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM ITEMS", null);

        int i= 0;
        while (cursor.moveToNext()){

            toDoItems.add(i, new ToDoList(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)));
            i++;
        }

        return toDoItems;
    }

    public ToDoList getItem(String taskName)
    {
        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM ITEMS WHERE TASK_NAME='" + taskName + "'", null);

        while (cursor.moveToNext())
            return new ToDoList(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));

        return new ToDoList("","","","","",-1);
    }
}
