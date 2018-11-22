package com.stephengoeddel;

public class RoundRobinScheduler extends Scheduler {
    @Override
    protected Process determineNextProcessToRun() {
        synchronized (processes) {
            // Remove the process that just finished running and is still marked current and add it to the end
            processes.remove(currentlyRunningProcess);
            processes.add(currentlyRunningProcess);
            return processes.stream()
                    .filter(process -> !process.isFinished())
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Every Process Was finished..."));
        }
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
