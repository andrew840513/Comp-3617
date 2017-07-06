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

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class AddTaskActivity extends AppCompatActivity {
    private TextView taskName;
    private TextView dueDate;
    private Button saveBtn;

    private Task task = new Task(Realm.getDefaultInstance());

    boolean hide = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        taskName = (TextView) findViewById(R.id.add_taskName);
        dueDate = (TextView) findViewById(R.id.add_dueDate);

        saveBtn = (Button) findViewById(R.id.add_saveBtn);


        taskName.setOnClickListener(onClickText());
        dueDate.setOnClickListener(onClickDate());
        saveBtn.setOnClickListener(onClickSave());
    }
    public View.OnClickListener onClickText() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Andrew",Boolean.toString(hide));
                hideKeyboard(view);
                taskName.clearFocus();
            }
        };
    }

    public View.OnClickListener onClickSave(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateText = (String)dueDate.getText();
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                try{
                    Date date = format.parse(dateText);
                    TaskModel newTask = new TaskModel(taskName.getText().toString(),date);
                    task.addNewTask(newTask);
                    Intent intent = new Intent(getApplicationContext(),DisplayTaskActivity.class);
                    startActivityForResult(intent,100);
                }catch (Exception e){
                    Log.d("Andrew",e.getMessage());
                }
            }
        };
    }

    public View.OnClickListener onClickDate(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("dueDateLbl",R.id.add_dueDate);
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(),"datePicker");
            }
        };
    }
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
