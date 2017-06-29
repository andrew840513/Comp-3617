package com.comp3617.assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3617.assignment2.util.Task;

import io.realm.Realm;

public class EditTaskActivity extends AppCompatActivity {
    TextView taskName;
    TextView dueDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Intent intent = getIntent();
        int ID = intent.getIntExtra("taskDetailID",0);
        final Task task = new Task(Realm.getDefaultInstance());
        TaskModel taskModel = task.getTask(ID);
        taskName = (TextView) findViewById(R.id.edit_taskName);
        dueDate = (TextView) findViewById(R.id.edit_dueDate);
        taskName.setText(taskModel.getTaskName());
        taskName.setEnabled(false);
        dueDate.setText(taskModel.getDueDate().toString());
    }

    public void textOnClick() {
        taskName.setEnabled(true);
        Toast.makeText(this,"This text clecked",Toast.LENGTH_LONG).show();
    }

}
