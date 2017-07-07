package com.comp3617.assignment2;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Andrew on 2017-06-28.
 */

public class TaskModel extends RealmObject {
    private boolean finished = false;
    private String taskName;
    private Date dueDate;
    private int ID;

    public TaskModel(){}

    public TaskModel(String taskName, Date dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
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

    public Date getDueDate(){ return dueDate;}

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setTaskModel(TaskModel task) {
        setTaskName(task.getTaskName());
        setDueDate(task.getDueDate());
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "finished=" + finished +
                ", taskName='" + taskName + '\'' +
                ", dueDate=" + dueDate +
                ", ID=" + ID +
                '}';
    }
}
