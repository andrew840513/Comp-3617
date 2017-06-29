package com.comp3617.assignment2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.comp3617.assignment2.util.Task;

import io.realm.Realm;

public class EditTaskActivity extends AppCompatActivity {
    TextView taskName;
    TextView dueDate;
    boolean hide = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent intent = getIntent();
        int ID = intent.getIntExtra("taskDetailID",0);
        final Task task = new Task(Realm.getDefaultInstance());
        TaskModel taskModel = task.getTask(ID);
        taskName = (TextView) findViewById(R.id.edit_taskName);
        dueDate = (TextView) findViewById(R.id.edit_dueDate);
        taskName.setText(taskModel.getTaskName());
        dueDate.setText(taskModel.getDueDate().toString());
        taskName.setOnClickListener(onClickText());
    }
    public View.OnClickListener onClickText(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Andrew",Boolean.toString(hide));
                hideKeyboard(view);
                taskName.clearFocus();
            }
        };
    }
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
