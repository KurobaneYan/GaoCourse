package com.kurobane.yan.gao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DbHelper dbHelper = DbHelper.getInstance(getApplicationContext());

        Button addGoal = (Button) findViewById(R.id.add_goal_button);


        if (addGoal != null) {
            addGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Goal goal = constructGoal();
                    if (goal != null) {
                        dbHelper.addGoal(goal);
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Goal constructGoal() {
        final EditText goalName = (EditText) findViewById(R.id.add_goal_name);
        final EditText goalDescription = (EditText) findViewById(R.id.add_goal_description);
        final EditText goalTasks = (EditText) findViewById(R.id.add_goal_tasks);

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
