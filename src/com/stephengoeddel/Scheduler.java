package com.stephengoeddel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

public abstract class Scheduler {
    protected List<Process> processes = new ArrayList<>();
    protected Process currentlyRunningProcess;


    public void run() {
        while (!allProcessesComplete()) {
            Process processToRun = determineNextProcessToRun();
            System.out.println("Now Running: " + processToRun);
            try {
                TimeUnit.MILLISECONDS.sleep(Timer.BURST_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace(); // oops
            }
            processToRun.runProcessFor(Timer.BURST_TIME);

            for (Process process : processes) {
                if (!process.equals(processToRun) && !process.isFinished()) {
                    process.incrementWaitTime(Timer.BURST_TIME);
                }
            }
        }

        printResults();
    }

    public void addProcess(Process process) {
        processes.add(process);
        internalAddProcess(process);
    }

    private boolean allProcessesComplete() {
        if (processes.isEmpty()) {
            return true;
        }

        return processes.stream()
                .allMatch(Process::isFinished);
    }

    private void printResults() {
        System.out.println("============ Results ============");
        processes.forEach(System.out::println);

        System.out.println("==== Total Wait Time ====");
        long totalWaitTime = processes.stream()
                .mapToLong(Process::getTotalWaitTime).sum();
        System.out.println(
                totalWaitTime
        );

        System.out.println("==== Average Wait Time ====");
        System.out.println(totalWaitTime / processes.size());
    }

    protected abstract Process determineNextProcessToRun();
    protected abstract void internalAddProcess(Process process);
}
