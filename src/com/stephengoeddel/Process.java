package com.stephengoeddel;

import java.util.Objects;

public class Process {
    private final int id;
    private final String name;
    private final long entryTime;
    private final long totalTime;
    private long timeRemaining;
    private long totalWaitTime;
    //TODO: add List of Tickets


    public Process(int id, String name, long entryTime, long totalTime) {
        this.id = id;
        this.name = name;
        this.entryTime = entryTime;
        this.totalTime = totalTime;
        this.timeRemaining = totalTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public long getTotalWaitTime() {
        return totalWaitTime;
    }

    public void incrementWaitTime(long waitTime) {
        totalWaitTime += waitTime;
    }

    public void runProcessFor(long time) {
        timeRemaining -= time;
    }

    public boolean isFinished() {
        return timeRemaining <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Process process = (Process) o;
        return id == process.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Process{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", entryTime=" + entryTime +
                ", totalTime=" + totalTime +
                ", totalWaitTime=" + totalWaitTime +
                '}';
    }
}
