package com.example.cesiappli.model;

public class Task {
    //Fields
    private int id;
    private String label;
    private boolean done;

    //Constructor
    public Task(String label) {
        //this.id = id;
        this.label = label;
        this.done = false;
    }

    //GetSet
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", done=" + done +
                '}';
    }
}
