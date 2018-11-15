package com.stephengoeddel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Process {
    private final int id;
    private final String name;
    private final long entryTime;
    private final long totalTime;
    private long timeRemaining;
    private List<Long> waitPeriods;
    private List<LotteryTicket> tickets;


    public Process(int id, String name, long entryTime, long totalTime) {
        this.id = id;
        this.name = name;
        this.entryTime = entryTime;
        this.totalTime = totalTime;
        this.timeRemaining = totalTime;
        waitPeriods = new ArrayList<>();
        waitPeriods.add(0L); // Initialize the first empty wait period
        tickets = new ArrayList<>();
    }

    public void resetProcess() {
        timeRemaining = totalTime;
        waitPeriods = new ArrayList<>();
        waitPeriods.add(0L); // Initialize the first empty wait period
        tickets = new ArrayList<>();
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
        return waitPeriods.stream().mapToLong(Long::longValue).sum();
    }

    public long getLongestWaitPeriod() {
        List<Long> waitPeriodCopy = new ArrayList<>(waitPeriods);
        waitPeriodCopy.sort(Collections.reverseOrder());
        return waitPeriodCopy.get(0);
    }

    public void notifyOfWait(long waitTime) {
        Long currentWaitChunk = waitPeriods.get(waitPeriods.size() - 1);
        waitPeriods.set(waitPeriods.size() - 1, currentWaitChunk + waitTime);
    }

    public void runProcessFor(long time) {
        timeRemaining -= time;
        waitPeriods.add(0L); // Begin a new wait period now that the process has had a burst
    }

    public boolean isFinished() {
        return timeRemaining <= 0;
    }

    public void addLotteryTicket(LotteryTicket lotteryTicket) {
        tickets.add(lotteryTicket);
    }

    public boolean hasWinningTicket(int ticketNumber) {
        return tickets.stream()
                .anyMatch(ticket -> ticket.isWinner(ticketNumber));
    }

    public void clearTickets() {
        tickets.clear();
    }

    public List<LotteryTicket> getTickets() {
        return tickets;
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
                ", timeRemaining=" + timeRemaining +
                ", totalWaitTime=" + getTotalWaitTime() +
                ", longestWaitPeriod=" + getLongestWaitPeriod() +
                '}';
    }
}
