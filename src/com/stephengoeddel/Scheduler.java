package com.stephengoeddel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class Scheduler implements Runnable {
    protected final List<Process> processes = new ArrayList<>();
    protected Process currentlyRunningProcess;
    private int totalBurstsRun = 0;


    @Override
    public void run() {
        while (!allProcessesComplete()) {
            Process processToRun = determineNextProcessToRun();
            currentlyRunningProcess = processToRun;
//            System.out.println("Now Running: " + processToRun);
            try {
                TimeUnit.MILLISECONDS.sleep(Timer.BURST_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace(); // oops
            }

            processToRun.runProcessFor(Timer.BURST_TIME);

            if (processToRun.isFinished()) {
                notifyOfFinishedProcess(processToRun);
            }

            synchronized (processes) {
                for (Process process : processes) {
                    if (!process.equals(processToRun) && !process.isFinished()) {
                        process.notifyOfWait(Timer.BURST_TIME);
                    }
                }
            }

            totalBurstsRun++;
        }

        printResults();
    }

    public void addProcess(Process process) {
        synchronized (processes) {
            processes.add(process);
            internalAddProcess(process);
        }
    }

    private boolean allProcessesComplete() {
        if (processes.isEmpty()) {
            return true;
        }

        synchronized (processes) {
            return processes.stream()
                    .allMatch(Process::isFinished);
        }
    }

    private void printResults() {
        System.out.println("============ Results ============");
        synchronized (processes) {
            processes.sort(Comparator.comparingInt(Process::getId));
        }
        processes.forEach(System.out::println);

        System.out.println("==== Total Bursts ====");
        System.out.println(totalBurstsRun);
        System.out.println("==== Total Wait Time ====");
        long totalWaitTime = processes.stream()
                .mapToLong(Process::getTotalWaitTime).sum();
        System.out.println(
                totalWaitTime
        );

        System.out.println("==== Average Wait Time ====");
        System.out.println(totalWaitTime / processes.size());

        System.out.println("==== Longest Wait Period ====");
        List<Long> longestWaitPeriods = processes.stream()
                .map(Process::getLongestWaitPeriod)
                .collect(Collectors.toList());
        longestWaitPeriods.sort(Comparator.reverseOrder());

        System.out.println(longestWaitPeriods.get(0));
    }

    protected abstract Process determineNextProcessToRun();
    protected abstract void internalAddProcess(Process process);
    protected abstract void notifyOfFinishedProcess(Process process);
}
