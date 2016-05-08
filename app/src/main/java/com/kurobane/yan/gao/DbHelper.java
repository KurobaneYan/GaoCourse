package com.kurobane.yan.gao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {
    private static DbHelper instance = null;

    private static final String DATABASE_NAME = "goaTest1";
    private static final int DATABASE_VERSION = 1;
    private static final String GOALS_TABLE_NAME = "goals";
    private static final String GOAL_ID = "id";
    private static final String GOAL_NAME = "name";
    private static final String GOAL_DESCRIPTION = "description";
    private static final String GOAL_FINISHED = "finished";
    private static final String GOAL_PUNISHED = "punished";

    private static final String[] GOAL_COLUMNS =
            {GOAL_ID, GOAL_NAME, GOAL_DESCRIPTION, GOAL_FINISHED, GOAL_PUNISHED};

    private static final String TASKS_TABLE_NAME = "tasks";
    private static final String TASK_ID = "task_id";
    private static final String TASK_KEY = "task_key";
    private static final String TASK_VALUE = "task_value";
    private static final String TASK_PARENT_KEY = "task_parent_key";

    private static final String[] TASK_COLUMNS =
            {TASK_ID, TASK_KEY, TASK_VALUE, TASK_PARENT_KEY};


    private DbHelper(Context applicationContext) {
        super(applicationContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static  DbHelper getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new DbHelper(applicationContext);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GOALS_TABLE = "CREATE TABLE " + GOALS_TABLE_NAME + " ( " +
                GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GOAL_NAME + " TEXT, " +
                GOAL_DESCRIPTION + " TEXT, " +
                GOAL_FINISHED + " INTEGER, " +
                GOAL_PUNISHED + " INTEGER )";
        db.execSQL(CREATE_GOALS_TABLE);

        String CREATE_TASKS_TABLE = "CREATE TABLE " + TASKS_TABLE_NAME + " ( " +
                TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TASK_KEY + " TEXT, " +
                TASK_VALUE + " INTEGER, " +
                TASK_PARENT_KEY + " TEXT, " +
                "FOREIGN KEY(" + TASK_PARENT_KEY + ") REFERENCES " + GOALS_TABLE_NAME + "(" + GOAL_NAME + ") )";
        db.execSQL(CREATE_TASKS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GOALS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE_NAME);
        this.onCreate(db);
    }

    public void addGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GOAL_NAME, goal.getName());
        values.put(GOAL_DESCRIPTION, goal.getDescription());
        values.put(GOAL_FINISHED, goal.getIsFinished());
        values.put(GOAL_PUNISHED, goal.getIsPunished());

        db.insert(GOALS_TABLE_NAME, null, values);
        db.close();

        addTasks(goal);
    }

    public Goal getGoal(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                GOALS_TABLE_NAME,
                GOAL_COLUMNS,
                GOAL_NAME + " = ?",
                new String[] { name },
                null,
                null,
                null);

        Goal goal = new Goal();
        if (cursor != null) {
            cursor.moveToFirst();

            goal.setId(cursor.getInt(0));
            goal.setName(cursor.getString(1));
            goal.setDescription(cursor.getString(2));
            goal.setIsFinished(cursor.getInt(3));
            goal.setIsPunished(cursor.getInt(4));
            goal.setTasks(getTasks(cursor.getString(1)));

            cursor.close();
        }

        return goal;
    }

    public int updateGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GOAL_NAME, goal.getName());
        values.put(GOAL_DESCRIPTION, goal.getDescription());
        values.put(GOAL_FINISHED, goal.getIsFinished());
        values.put(GOAL_PUNISHED, goal.getIsPunished());

        int i = db.update(GOALS_TABLE_NAME,
                values,
                GOAL_NAME + " = ?",
                new String[] {String.valueOf(goal.getName())});

        db.close();

        updateTasks(goal);

        return i;
    }

    public void deleteGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(GOALS_TABLE_NAME,
                GOAL_NAME + " = ?",
                new String[] { String.valueOf(goal.getName())} );

        db.close();

        deleteTasks(goal);
    }

    public ArrayList<Goal> getAllGoals() {
        ArrayList<Goal> goals = new ArrayList<>();

        String query = "Select * FROM " + GOALS_TABLE_NAME;

        // maybe change to writable
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                Goal goal = new Goal();
                goal.setId(cursor.getInt(0));
                goal.setName(cursor.getString(1));
                goal.setDescription(cursor.getString(2));
                goal.setIsFinished(cursor.getInt(3));
                goal.setIsPunished(cursor.getInt(4));
                goal.setTasks(getTasks(goal.getName()));

                goals.add(goal);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        Log.d("getAllGoals()", goals.toString());
        return goals;
    }

    public void addTasks(Goal goal) {
        HashMap<String, Integer> hashMap = goal.getTasks();
        SQLiteDatabase db = this.getWritableDatabase();

        for (String key : hashMap.keySet()) {
            //int value = hashMap.get(key);

            ContentValues values = new ContentValues();
            values.put(TASK_KEY, key);
            values.put(TASK_VALUE, hashMap.get(key));
            values.put(TASK_PARENT_KEY, goal.getName());
            db.insert(TASKS_TABLE_NAME, null, values);
        }

        db.close();

        Log.d("addTasks()", hashMap.toString());
    }

    public HashMap<String, Integer> getTasks(String parent_name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TASKS_TABLE_NAME,
                TASK_COLUMNS,
                TASK_PARENT_KEY + " = ?",
                new String[] {parent_name},
                null,
                null,
                null);

        HashMap<String, Integer> hashMap = new HashMap<>();

        if (cursor.moveToFirst()){
            do {
                hashMap.put(cursor.getString(1), cursor.getInt(2));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        Log.d("getTasks()", hashMap.toString());
        return  hashMap;
    }

    public void updateTasks(Goal goal) {
        HashMap<String, Integer> hashMap = goal.getTasks();
        SQLiteDatabase db = this.getWritableDatabase();

        for (String key : hashMap.keySet()) {
            ContentValues values = new ContentValues();
            values.put(TASK_KEY, key);
            values.put(TASK_VALUE, hashMap.get(key));

            int i = db.update(TASKS_TABLE_NAME,
                    values,
                    TASK_KEY + " = ?",
                    new String[] { key });
        }

        db.close();
    }

    public void deleteTasks(Goal goal) {
        HashMap<String, Integer> hashMap = goal.getTasks();
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TASKS_TABLE_NAME,
                TASK_PARENT_KEY + " = ?",
                new String[] {goal.getName()});

        db.close();
    }
}
