package com.stephengoeddel;

public class ShortestRemainingTimeScheduler extends Scheduler {

    @Override
    protected Process determineNextProcessToRun() {
        Process processToRun = null;
        for (Process process : processes) {
            if (!process.isFinished()) {
                if (processToRun == null || processToRun.getTimeRemaining() > process.getTimeRemaining()) {
                    processToRun = process;
                }
            }
        }

        return processToRun;
    }

    @Override
    protected void internalAddProcess(Process process) {
        if (currentlyRunningProcess == null) {
            currentlyRunningProcess = process;
        }
    }
}
