package com.stephengoeddel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private static final int NUMBER_OF_SEED_PROCESSES = 10;
    private static final int MAX_PROCESS_LENGTH = 1000;
    private static final int MIN_PROCESS_LENGTH = 100;

    public static void main(String[] args) {
        List<Process> seedProcesses = createSeedProcesses();

        Scheduler srts = new ShortestRemainingTimeFirstScheduler();
        seedProcesses.forEach(srts::addProcess);
        System.out.println("================== Shortest Remaining Time Scheduler ==================");
        srts.run();

        seedProcesses.forEach(Process::resetProcess);

        Scheduler lotteryScheduler = new BasicLotteryScheduler();
        seedProcesses.forEach(lotteryScheduler::addProcess);
        System.out.println("================== Lottery Scheduler ==================");
        lotteryScheduler.run();

        seedProcesses.forEach(Process::resetProcess);

        Scheduler srtfLotteryScheduler = new ShortestRemainingTimeFirstLotteryScheduler();
        seedProcesses.forEach(srtfLotteryScheduler::addProcess);
        System.out.println("================== Shortest Remaining Time Lottery Scheduler ==================");
        srtfLotteryScheduler.run();
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
