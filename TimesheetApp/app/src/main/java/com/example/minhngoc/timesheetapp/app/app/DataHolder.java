package com.example.minhngoc.timesheetapp.app.app;

/**
 * Created by minhngoc on 11/30/15.
 */
public class DataHolder {
    private long startTime;
    private long stopTime;
    private int secondsElapsed;
    private int totalTime;

    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {
        return holder;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public int getSecondsElapsed() {
        return secondsElapsed;
    }

    public int getTotalTime() {
        return totalTime;
    }
}
