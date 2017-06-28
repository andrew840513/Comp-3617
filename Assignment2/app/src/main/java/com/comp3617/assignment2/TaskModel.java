package com.comp3617.assignment2;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

import static android.os.Build.ID;

/**
 * Created by Andrew on 2017-06-28.
 */

public class TaskModel extends RealmObject {
    private String taskName;
    private Date dueDate;
    private Date startDate;
    private int ID;

    public TaskModel(){}

    public TaskModel(String taskName, Date dueDate, Date startDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.startDate = startDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setTaskModel(TaskModel task) {
        setTaskName(task.getTaskName());
        setDueDate(task.getDueDate());
        setStartDate(task.getStartDate());
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "taskName='" + taskName + '\'' +
                ", dueDate=" + dueDate +
                ", startDate=" + startDate +
                ", ID=" + ID +
                '}';
    }
}
