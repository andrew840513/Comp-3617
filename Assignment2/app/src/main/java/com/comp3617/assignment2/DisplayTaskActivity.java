package com.comp3617.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.comp3617.assignment2.util.Task;

import io.realm.Realm;

public class DisplayTaskActivity extends AppCompatActivity {
    ListView taskListView;
    Realm realm;
    TaskListAdapter adapter;
    Task task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);

        //Realm
        realm = Realm.getDefaultInstance();
        task = new Task(realm);

        //ListView
        taskListView = (ListView) findViewById(R.id.list_view);
        adapter = new TaskListAdapter(this,task.getAllTask());
        taskListView.setAdapter(adapter);

        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Add button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(DisplayTaskActivity.this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.add_new_task) {
            addTask();
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addTask(){
        Intent intent = new Intent(getApplication(), AddTaskActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new TaskListAdapter(this,task.getAllTask());
        taskListView.setAdapter(adapter);
    }
}
