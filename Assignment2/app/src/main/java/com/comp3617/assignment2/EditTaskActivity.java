package com.comp3617.assignment2;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3617.assignment2.util.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class EditTaskActivity extends AppCompatActivity {
    private TextView taskName;
    protected TextView dueDate;
    protected Button deleteBtn;
    protected Button saveBtn;
    protected Button emailBtn;
    protected Button calendarBtn;
    CheckBox finishBox;
    TaskModel taskModel;
    private Task task = new Task(Realm.getDefaultInstance());

    private int ID;

    boolean hide = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent = getIntent();
        ID = intent.getIntExtra("taskDetailID",0);

        taskModel = task.getTask(ID);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean email = prefs.getBoolean("enable_share_task",false);

        emailBtn = (Button) findViewById(R.id.emailBtn);
        emailBtn.setVisibility(email? View.VISIBLE:View.GONE);

        calendarBtn = (Button) findViewById(R.id.reminderBtn);
        calendarBtn.setVisibility(View.GONE);

        taskName = (TextView) findViewById(R.id.taskName);
        dueDate = (TextView) findViewById(R.id.dueDate);
        deleteBtn = (Button) findViewById(R.id.cancelAndDelete);
        saveBtn = (Button) findViewById(R.id.save);
        finishBox = (CheckBox) findViewById(R.id.finishBox);

        taskName.setText(taskModel.getTaskName());
        dueDate.setText(taskModel.getDueDate().toString());
        deleteBtn.setText(R.string.delete);
        finishBox.setChecked(taskModel.isFinished());

        taskName.setOnClickListener(onClickText());
        dueDate.setOnClickListener(onClickDate());
        deleteBtn.setOnClickListener(onDelete());
        saveBtn.setOnClickListener(onSave());
        if(email){
            emailBtn.setOnClickListener(onCLickEmail());
        }
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
                TaskDeleteDialogFragment fragment = new TaskDeleteDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("ID",ID);
                fragment.setArguments(bundle);
                //task.deleteTask(ID);
                fragment.show(getFragmentManager(), "delete");
            }
        };
    }

    public View.OnClickListener onSave(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateText = (String)dueDate.getText();
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
                try{
                    Date date = format.parse(dateText);
                    TaskModel newTask = new TaskModel(taskName.getText().toString(),date);
                    task.editTask(newTask,ID);
                    task.setFinish(ID,finishBox.isChecked());
                    finish();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),R.string.enter_date,Toast.LENGTH_LONG).show();
                }
            }
        };
    }
    public View.OnClickListener onCLickEmail(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder message = new StringBuilder();
                message.append(String.format("%-16s %s%n","Name:",taskModel.getTaskName()));
                message.append(String.format("%-16s %s%n","Due Date:",taskModel.getDueDate().toString()));
                message.append(String.format("%-16s %s%n","Completed:",taskModel.isFinished()? "Yes": "No"));
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Task: "+taskModel.getTaskName());
                intent.putExtra(Intent.EXTRA_TEXT, message.toString());
                startActivity(intent);
            }
        };
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    public static class TaskDeleteDialogFragment extends DialogFragment{
        private Task task = new Task(Realm.getDefaultInstance());
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final int ID = getArguments().getInt("ID");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Confirm");
            builder.setMessage("Are you sure?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    task.deleteTask(ID);
                    dialog.dismiss();
                    getActivity().finish();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }
}