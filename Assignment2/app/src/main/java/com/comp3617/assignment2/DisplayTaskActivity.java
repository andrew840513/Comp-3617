package com.comp3617.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.comp3617.assignment2.util.Task;

import java.util.Date;

import io.realm.Realm;

public class DisplayTaskActivity extends AppCompatActivity {
    ListView taskListView;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);

        //Realm
        realm = Realm.getDefaultInstance();
        Task task = new Task(realm);
        for(int i=0;i<20;i++) {
            task.addNewTask(new TaskModel("Test"+ Integer.toString(i),new Date(),new Date()));
        }

        //ListView
        taskListView = (ListView) findViewById(R.id.list_view);
        TaskListAdapter adapter = new TaskListAdapter(this,task.getAllTask());
        taskListView.setAdapter(adapter);

        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Add button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
            Toast.makeText(getApplicationContext(),"Add",Toast.LENGTH_SHORT).show();
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }
}
