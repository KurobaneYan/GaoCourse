package com.kurobane.yan.gao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditGoalActivity extends AppCompatActivity {
    DbHelper dbHelper;
    String goalName;
    Goal goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("goalName")) {
            goalName = intent.getStringExtra("goalName");
            Log.d("getIntentExtra", goalName);

            goal = dbHelper.getGoal(goalName);
            showGoal(goal);
        }

        setButton();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showGoal(Goal goal) {
        EditText editGoalName = (EditText) findViewById(R.id.edit_goal_name);
        EditText editGoalDescription = (EditText) findViewById(R.id.edit_goal_description);
        EditText editGoalTasks = (EditText) findViewById(R.id.edit_goal_tasks);

        if (editGoalName != null) {
            editGoalName.setText(goal.getName());
        }
        if (editGoalDescription != null) {
            editGoalDescription.setText(goal.getDescription());
        }

        String tempTasks = "";
        for (Task task : goal.getTasks()) {
            tempTasks += task.getName() + "\n";
        }

        if (editGoalTasks != null) {
            editGoalTasks.setText(tempTasks);
        }
    }

    private void setButton() {
        Button saveGoal = (Button) findViewById(R.id.edit_goal_save);
        if (saveGoal != null) {
            saveGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.deleteGoal(goal);
                    Goal newGoal = constructGoal();
                    dbHelper.addGoal(newGoal);
                    Intent intent = new Intent(getApplicationContext(), GoalDetailActivity.class);
                    intent.putExtra("goalName", newGoal.getName());
                    startActivity(intent);
                }
            });
        }
    }

    private Goal constructGoal() {
        final EditText goalName = (EditText) findViewById(R.id.edit_goal_name);
        final EditText goalDescription = (EditText) findViewById(R.id.edit_goal_description);
        final EditText goalTasks = (EditText) findViewById(R.id.edit_goal_tasks);

        Goal goal = null;

        if ((goalName != null) && (goalDescription != null) && (goalTasks != null)) {
            goal = new Goal();
            goal.setName(String.valueOf(goalName.getText()));
            goal.setDescription(String.valueOf(goalDescription.getText()));
            ArrayList<Task> tasksArray = new ArrayList<>();
            String tasks = String.valueOf(goalTasks.getText()) + "\n";
            for (String key : tasks.split("\\n")) {
                if (!key.equals("")) {
                    tasksArray.add(new Task(key));
                }
            }

            goal.setTasks(tasksArray);
        }

        return goal;
    }
}
