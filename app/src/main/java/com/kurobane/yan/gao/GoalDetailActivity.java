package com.kurobane.yan.gao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class GoalDetailActivity extends AppCompatActivity {
    DbHelper dbHelper;
    String goalName;
    Goal goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("goalName")) {
            goalName = intent.getStringExtra("goalName");
            Log.d("getIntentExtra", goalName);

            goal = dbHelper.getGoal(goalName);
            showGoal(goal);

            setupButtons();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showGoal(Goal goal) {
        TextView goalName = (TextView) findViewById(R.id.detail_goal_name);
        TextView goalDescription = (TextView) findViewById(R.id.detail_goal_description);
        TextView goalTasks = (TextView) findViewById(R.id.detail_goal_tasks);

        if ((goalName != null) && (goalDescription != null) && (goalTasks != null)) {
            goalName.setText(goal.getName());
            goalDescription.setText(goal.getDescription());

            ArrayList<Task> tasks = goal.getTasks();
            goalTasks.setText(tasks.toString());
        }
    }

    private void setupButtons() {
        final Button edit = (Button) findViewById(R.id.detail_edit);
        Button delete = (Button) findViewById(R.id.detail_delete);

        if (edit != null) {
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ReplaceEdit!", edit.toString());

                }
            });
        }

        if (delete != null) {
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.deleteGoal(goal);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
