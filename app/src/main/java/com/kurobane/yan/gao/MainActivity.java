package com.kurobane.yan.gao;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DbHelper dbHelper = DbHelper.getInstance(this);
        final ArrayList<Goal> goals = dbHelper.getAllGoals();

        final GoalsAdapter adapter = new GoalsAdapter(this, goals);

        Log.d("goals", goals.toString());

        ListView listView = (ListView) findViewById(R.id.goals_list);
        if (listView != null) {
            listView.setAdapter(adapter);
        }

//        populateUsersList();
        HashMap<String, Integer> hm = new HashMap<>();
        hm.put("Start", 1);
        hm.put("Second", 0);
        final Goal test = new Goal();
        test.setName("Test");
        test.setDescription("Descr");
        test.setIsFinished(0);
        test.setIsPunished(0);
        test.setTasks(hm);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    //dbHelper.deleteGoal(goals.get(0));
                    //dbHelper.addGoal(test);
                    dbHelper.deleteGoal(test);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private void populateUsersList() {

        ArrayList<Goal> goals = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            goals.add(new Goal("Goal " + String.valueOf(i), "Some description"));
        }

        GoalsAdapter adapter = new GoalsAdapter(this, goals);

        Log.d("Goals", goals.toString());

        ListView listView = (ListView) findViewById(R.id.goals_list);
        if (listView != null) {
            listView.setAdapter(adapter);
        }
    }

}
