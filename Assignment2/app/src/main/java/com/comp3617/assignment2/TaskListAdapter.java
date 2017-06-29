package com.comp3617.assignment2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3617.assignment2.util.Task;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Andrew on 2017-06-28.
 */

public class TaskListAdapter extends ArrayAdapter<TaskModel> {
    private final Context ctx;
    private List<TaskModel> task;

    public TaskListAdapter(Context ctx, List<TaskModel> task) {
        super(ctx,0,task);
        this.ctx = ctx;
        this.task = task;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = null;
        final TaskModel taskModel = task.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_prototype_layout, parent, false);
        } else {
            rowView = convertView;
        }
        TextView taskName = (TextView) rowView.findViewById(R.id.nameLbl);
        TextView startDate = (TextView) rowView.findViewById(R.id.dateLbl);

        taskName.setText(taskModel.getTaskName());
        startDate.setText(taskModel.getStartDate().toString());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "I clicked task "+ taskModel.getTaskName()+" ID:"+ Integer.toString(taskModel.getID()), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}
