package com.stephengoeddel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private static int NUMBER_OF_SEED_PROCESSES = 10;
    private static int MAX_PROCESS_LENGTH = 1000;
    private static int MIN_PROCESS_LENGTH = 100;

    public static void main(String[] args) {
        Scheduler srts = new ShortestRemainingTimeScheduler();
        List<Process> seedProcesses = createSeedProcesses();
        seedProcesses.forEach(srts::addProcess);
        System.out.println("================== Shortest Remaining Time Scheduler ==================");
        srts.run();

        seedProcesses.forEach(Process::resetTimeRemaining);

        Scheduler lotteryScheduler = new LotteryScheduler();
        seedProcesses.forEach(lotteryScheduler::addProcess);
        System.out.println("================== Lottery Scheduler ==================");
        lotteryScheduler.run();
    }

    private static List<Process> createSeedProcesses() {
        List<Process> processes = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_SEED_PROCESSES; i++) {
            int processTime = ThreadLocalRandom.current().nextInt(MIN_PROCESS_LENGTH, MAX_PROCESS_LENGTH + 1);
            processes.add(
                    new Process(i, "Seed Process " + i, Timer.getCurrentTime(), processTime)
            );
        }

        return processes;
    }
}
