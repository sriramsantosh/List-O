package com.aripi.todoapp.data;

import java.util.ArrayList;

/**
 * Created by saripirala on 8/21/17.
 */

public class ToDoList {

    public String taskName;
    public String taskNotes;
    public String dueDate;
    public String priority;
    public String status;
    public int position;

    public ToDoList(String taskName, String dueDate, String taskNotes, String priority, String status, int position)
    {
        this.taskName = taskName;
        this.taskNotes = taskNotes;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.position = position;
    }

    public String getTaskName()
    {
        return this.taskName;
    }


    public String getTaskPriority()
    {
        return this.priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getTaskNotes(){
        return taskNotes;
    }

    public String getStatus() {
        return status;
    }
}
