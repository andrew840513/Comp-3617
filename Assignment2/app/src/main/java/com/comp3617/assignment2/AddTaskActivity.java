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

public class AddTaskActivity extends AppCompatActivity {
    private TextView taskName;
    private TextView dueDate;
    private Button saveBtn;
    private Button cancelBtn;
    private Task task = new Task(Realm.getDefaultInstance());

    boolean hide = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        taskName = (TextView) findViewById(R.id.taskName);
        dueDate = (TextView) findViewById(R.id.dueDate);

        saveBtn = (Button) findViewById(R.id.save);
        cancelBtn = (Button) findViewById(R.id.cancelAndDelete);

        cancelBtn.setText("Cancel");

        taskName.setOnClickListener(onClickText());
        dueDate.setOnClickListener(onClickDate());
        saveBtn.setOnClickListener(onClickSave());
        cancelBtn.setOnClickListener(onClickCancel());
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
                    Toast.makeText(getApplicationContext(),"You need to enter date",Toast.LENGTH_LONG).show();
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
                bundle.putInt("dueDateLbl",R.id.dueDate);
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(),"datePicker");
            }
        };
    }

    public View.OnClickListener onClickCancel(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
    }
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
