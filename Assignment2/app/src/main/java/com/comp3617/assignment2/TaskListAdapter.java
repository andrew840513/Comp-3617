package com.comp3617.assignment2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView taskName = rowView.findViewById(R.id.list_nameLbl);
        TextView startDate = rowView.findViewById(R.id.list_dateLbl);
        CheckBox checkBox = rowView.findViewById(R.id.list_checkBox);
        taskName.setText(taskModel.getTaskName());
        startDate.setText(taskModel.getDueDate().toString());
        checkBox.setChecked(taskModel.isFinished());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EditTaskActivity.class);
                intent.putExtra("taskDetailID", taskModel.getID());
                ctx.startActivity(intent);
                Toast.makeText(ctx, "I clicked task "+ taskModel.getTaskName()+" ID:"+ Integer.toString(taskModel.getID()), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}
