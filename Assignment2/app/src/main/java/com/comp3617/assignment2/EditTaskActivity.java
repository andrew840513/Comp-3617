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
import android.widget.Toast;

import com.comp3617.assignment2.util.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class EditTaskActivity extends AppCompatActivity {
    private TextView taskName;
    protected TextView dueDate;
    protected Button deleteBtn;
    protected Button saveBtn;

    private Task task = new Task(Realm.getDefaultInstance());

    private int ID;

    boolean hide = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent = getIntent();
        ID = intent.getIntExtra("taskDetailID",0);

        TaskModel taskModel = task.getTask(ID);

        taskName = (TextView) findViewById(R.id.taskName);
        dueDate = (TextView) findViewById(R.id.dueDate);
        deleteBtn = (Button) findViewById(R.id.cancelAndDelete);
        saveBtn = (Button) findViewById(R.id.save);

        taskName.setText(taskModel.getTaskName());
        dueDate.setText(taskModel.getDueDate().toString());
        deleteBtn.setText("Delete");

        taskName.setOnClickListener(onClickText());
        dueDate.setOnClickListener(onClickDate());
        deleteBtn.setOnClickListener(onDelete());
        saveBtn.setOnClickListener(onSave());
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
                Bundle bundle = new Bundle();
                bundle.putInt("dueDateLbl",R.id.dueDate);
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(),"datePicker");
            }
        };
    }

    public View.OnClickListener onDelete(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO add delete dialog
                task.deleteTask(ID);
                Intent intent = new Intent(getApplicationContext(), DisplayTaskActivity.class);
                startActivityForResult(intent,100);
            }
        };
    }

    public View.OnClickListener onSave(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateText = (String)dueDate.getText();
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                try{
                    Date date = format.parse(dateText);
                    TaskModel newTask = new TaskModel(taskName.getText().toString(),date);
                    task.editTask(newTask,ID);
                    Intent intent = new Intent(getApplicationContext(),DisplayTaskActivity.class);
                    startActivityForResult(intent,100);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"You need to enter date",Toast.LENGTH_LONG).show();
                    Log.d("Andrew",e.getMessage());
                }
            }
        };
    }
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
