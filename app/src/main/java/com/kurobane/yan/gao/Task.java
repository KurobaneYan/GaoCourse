package com.kurobane.yan.gao;

public class Task {
    private String name;
    private int value;

    public Task() {
        setValue(0);
    }

    public Task(String name) {
        setName(name);
        setValue(0);
    }

    public Task(String name, int value) {
        setName(name);
        setValue(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value > 0) {
            this.value = 1;
        }
        this.value = 0;
    }

    @Override
    public String toString() {
        return getName() + " " + String.valueOf(getValue());
    }
}
