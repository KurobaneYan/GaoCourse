package com.kurobane.yan.gao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Goal {
    private int id;
    private String name;
    private String description;
    private Boolean isFinished;
    private Boolean isPunished;

    private HashMap<String, Boolean> tasks;

    public Goal() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, Boolean> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<String, Boolean> tasks) {
        this.tasks = tasks;
    }

    public int getIsFinished() {
        if (isFinished) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished > 0;
    }

    public int getIsPunished() {
        if (isPunished) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setIsPunished(int isPunished) {
        this.isPunished = isPunished > 0;
    }

    public void saveTasks() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(getId()));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(getTasks());
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTasks() {

    }

    public void deleteTasks() {

    }

}
