package com.comp3617.assignment2.util;

import android.util.Log;

import com.comp3617.assignment2.TaskModel;

import java.util.ArrayList;

import io.realm.Realm;


/**
 * Created by Andrew on 2017-06-28.
 */

public class Task {
    private Realm realm;
    public Task(Realm realm) {
        this.realm = realm;
    }

    public ArrayList<TaskModel> getAllTask() {
        ArrayList<TaskModel> tasks = new ArrayList<>();
        for (TaskModel task: realm.where(TaskModel.class).findAll()) {
            if(task.getTaskName() !=null){
                tasks.add(task);
            }
        }
        return tasks;
    }

    public TaskModel getTask(final int ID) {

        final TaskModel searchTask = realm.where(TaskModel.class).equalTo("ID",ID).findFirst();
        return searchTask;
    }

    public void addNewTask(final TaskModel task) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int nextID = (int) realm.where(TaskModel.class).count();
                TaskModel newTask = realm.createObject(TaskModel.class);
                newTask.setTaskName(task.getTaskName());
                newTask.setDueDate(task.getDueDate());
                newTask.setFinished(task.isFinished());
                newTask.setID(nextID);
                Log.d("Andrew",newTask.toString());
            }
        });
    }

    public void editTask(final TaskModel task, int ID){
        final TaskModel updateTask = realm.where(TaskModel.class).equalTo("ID",ID).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                updateTask.setTaskModel(task);
            }
        });
    }

    public void setFinish(final int ID, final boolean isfinish) {
        final TaskModel updateTask = realm.where(TaskModel.class).equalTo("ID",ID).findFirst();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                updateTask.setFinished(isfinish);
            }
        });
    }
    public void deleteTask(final int ID) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(TaskModel.class).equalTo("ID",ID).findFirst().deleteFromRealm();
            }
        });
    }
}
