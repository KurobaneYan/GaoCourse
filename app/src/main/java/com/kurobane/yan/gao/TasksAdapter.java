package com.kurobane.yan.gao;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class TasksAdapter extends ArrayAdapter<Task> {
    DbHelper dbHelper;
    Goal goal;

    public TasksAdapter(Context context, Goal goal) {
        super(context, 0, goal.getTasks());
        this.goal = goal;
        dbHelper = DbHelper.getInstance(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Task task = getItem(position);
        String taskName = task.getName();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }

        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.task_checkbox);
        checkBox.setText(taskName);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("C", String.valueOf(isChecked));
                Task t = getItem(position);
                if (isChecked) {
                    t.setValue(0);
                } else {
                    t.setValue(1);
                }
                Log.d("item", t.toString());
            }
        });

        if (task.getValue() == 1) {
            checkBox.setChecked(true);
        }
        checkBox.setChecked(false);

        return convertView;
    }
}
