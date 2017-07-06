package com.comp3617.assignment2;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.comp3617.assignment2.util.Task;

import io.realm.Realm;

public class EditTaskActivity extends AppCompatActivity {
    private TextView taskName;
    private TextView dueDate;
    private Button deleteBtn;
    private Task task = new Task(Realm.getDefaultInstance());
    private int ID;
    boolean hide = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent = getIntent();
        ID = intent.getIntExtra("taskDetailID",0);

        TaskModel taskModel = task.getTask(ID);

        taskName = (TextView) findViewById(R.id.edit_taskName);
        dueDate = (TextView) findViewById(R.id.edit_dueDate);
        deleteBtn = (Button) findViewById(R.id.edit_delete);

        taskName.setText(taskModel.getTaskName());
        dueDate.setText(taskModel.getDueDate().toString());

        taskName.setOnClickListener(onClickText());
        dueDate.setOnClickListener(onClickDate());
        deleteBtn.setOnClickListener(onDelete());
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

    public View.OnClickListener onClickDate(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getFragmentManager(),"datePicker");
            }
        };
    }

    public View.OnClickListener onDelete(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.deleteTask(ID);
                Intent intent = new Intent(getApplicationContext(), DisplayTaskActivity.class);
                startActivityForResult(intent,100);
            }
        };
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
