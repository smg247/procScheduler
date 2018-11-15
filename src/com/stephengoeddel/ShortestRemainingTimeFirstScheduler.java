package com.stephengoeddel;

public class ShortestRemainingTimeFirstScheduler extends Scheduler {

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

    @Override
    protected void notifyOfFinishedProcess(Process process) {
        // No need to do anything here
    }
}
