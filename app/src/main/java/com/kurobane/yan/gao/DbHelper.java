package com.kurobane.yan.gao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "goaTest1";
    private static final int DATABASE_VERSION = 1;
    private static final String GOALS_TABLE_NAME = "goals";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_FINISHED = "finished";
    private static final String KEY_PUNISHED = "punished";

    private static final String[] COLUMNS =
            {KEY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_FINISHED, KEY_PUNISHED};

    public DbHelper(Context applicationContext) {
        super(applicationContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + GOALS_TABLE_NAME + " ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_DESCRIPTION + " TEXT, " +
                KEY_FINISHED + " INTEGER, " +
                KEY_PUNISHED + " INTEGER )";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GOALS_TABLE_NAME);
        this.onCreate(db);
    }

    public void addGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, goal.getName());
        values.put(KEY_DESCRIPTION, goal.getDescription());
        values.put(KEY_FINISHED, goal.getIsFinished());
        values.put(KEY_PUNISHED, goal.getIsPunished());

        db.insert(GOALS_TABLE_NAME, null, values);
        db.close();
    }

    public Goal getGoal(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                GOALS_TABLE_NAME,
                COLUMNS,
                " id = ?",
                null,
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

            cursor.close();
        }
        return goal;
    }

    public int updateGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, goal.getName());
        values.put(KEY_DESCRIPTION, goal.getDescription());
        values.put(KEY_FINISHED, goal.getIsFinished());
        values.put(KEY_PUNISHED, goal.getIsPunished());

        int i = db.update(GOALS_TABLE_NAME,
                values,
                KEY_ID + " = ?",
                new String[] {String.valueOf(goal.getId())});

        db.close();

        return i;
    }

    public void deleteGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(GOALS_TABLE_NAME,
                KEY_ID + " = ?",
                new String[] {String.valueOf(goal.getId())});

        db.close();
    }

    public List<Goal> getAllGoals() {
        List<Goal> goals = new ArrayList<>();

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

                goals.add(goal);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        Log.d("getAllGoals()", goals.toString());
        return goals;
    }

}
