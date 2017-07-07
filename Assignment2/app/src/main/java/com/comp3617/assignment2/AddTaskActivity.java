package com.comp3617.assignment2;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3617.assignment2.util.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class AddTaskActivity extends AppCompatActivity {
    private TextView taskName;
    private TextView dueDate;
    protected Button saveBtn;
    protected Button cancelBtn;
    protected Button emailBtn;
    protected Button calendarBtn;
    private Task task = new Task(Realm.getDefaultInstance());

    boolean hide = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean calendar = prefs.getBoolean("enable_addTo_calendar",false);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        CheckBox finishBox = (CheckBox) findViewById(R.id.finishBox);
        finishBox.setVisibility(View.GONE);

        emailBtn = (Button) findViewById(R.id.emailBtn);
        emailBtn.setVisibility(View.GONE);

        calendarBtn = (Button) findViewById(R.id.reminderBtn);
        calendarBtn.setVisibility(calendar? View.VISIBLE:View.GONE);

        taskName = (TextView) findViewById(R.id.taskName);
        dueDate = (TextView) findViewById(R.id.dueDate);
        saveBtn = (Button) findViewById(R.id.save);
        cancelBtn = (Button) findViewById(R.id.cancelAndDelete);


        ViewGroup.LayoutParams layoutParams = taskName.getLayoutParams();
        cancelBtn.setText(R.string.cancel);
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        taskName.setLayoutParams(layoutParams);

        taskName.setOnClickListener(onClickText());
        dueDate.setOnClickListener(onClickDate());
        saveBtn.setOnClickListener(onClickSave());
        cancelBtn.setOnClickListener(onClickCancel());


        if(calendar){
            calendarBtn.setOnClickListener(onCLickCalendar());
        }
    }
    public View.OnClickListener onClickText() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(getString(R.string.logTag),Boolean.toString(hide));
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
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
                try{
                    Date date = format.parse(dateText);
                    TaskModel newTask = new TaskModel(taskName.getText().toString(),date);
                    task.addNewTask(newTask);
                    finish();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),R.string.enter_date,Toast.LENGTH_LONG).show();
                    Log.d(getString(R.string.logTag),e.getMessage());
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

    public View.OnClickListener onCLickCalendar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateText = (String) dueDate.getText();
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
                try {
                    Date date = format.parse(dateText);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra("beginTime", calendar.getTimeInMillis());
                    intent.putExtra("allDay", false);
                    intent.putExtra("endTime", calendar.getTimeInMillis());
                    intent.putExtra("title", taskName.getText());
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), R.string.enter_date,Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
