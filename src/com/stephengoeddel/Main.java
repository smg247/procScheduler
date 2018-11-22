package com.stephengoeddel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private static final int NUMBER_OF_SEED_PROCESSES = 10;
    private static final int NUMBER_OF_ONGOING_PROCESSES = 30;
    private static final int MAX_PROCESS_LENGTH = 1000;
    private static final int MIN_PROCESS_LENGTH = 100;

    public static void main(String[] args) throws InterruptedException {
        List<Process> seedProcesses = createSeedProcesses();
        List<Process> ongoingProcesses = createOngoingProcesses();

        Scheduler roundRobinScheduler = new RoundRobinScheduler();
        seedProcesses.forEach(roundRobinScheduler::addProcess);
        System.out.println("================== Round Robin Scheduler ==================");
        Thread roundRobinThread = new Thread(roundRobinScheduler);
        roundRobinThread.start();

        for (Process ongoingProcess : ongoingProcesses) {
            roundRobinScheduler.addProcess(ongoingProcess);
            Thread.sleep(Timer.BURST_TIME/4);
        }

        roundRobinThread.join();

        seedProcesses.forEach(Process::resetProcess);
        ongoingProcesses.forEach(Process::resetProcess);

        Scheduler srts = new ShortestRemainingTimeFirstScheduler();
        seedProcesses.forEach(srts::addProcess);
        System.out.println("================== Shortest Remaining Time Scheduler ==================");
        Thread srtsThread = new Thread(srts);
        srtsThread.start();

        for (Process ongoingProcess : ongoingProcesses) {
            srts.addProcess(ongoingProcess);
            Thread.sleep(Timer.BURST_TIME/4);
        }

        srtsThread.join();

        seedProcesses.forEach(Process::resetProcess);
        ongoingProcesses.forEach(Process::resetProcess);

        Scheduler lotteryScheduler = new BasicLotteryScheduler();
        seedProcesses.forEach(lotteryScheduler::addProcess);
        System.out.println("================== Lottery Scheduler ==================");
        Thread lotteryThread = new Thread(lotteryScheduler);
        lotteryThread.start();

        for (Process ongoingProcess : ongoingProcesses) {
            lotteryScheduler.addProcess(ongoingProcess);
            Thread.sleep(Timer.BURST_TIME/4);
        }

        lotteryThread.join();

        seedProcesses.forEach(Process::resetProcess);
        ongoingProcesses.forEach(Process::resetProcess);

        Scheduler srtfLotteryScheduler = new ShortestRemainingTimeFirstLotteryScheduler();
        seedProcesses.forEach(srtfLotteryScheduler::addProcess);
        System.out.println("================== Shortest Remaining Time Lottery Scheduler ==================");
        Thread srtfLotteryThread = new Thread(srtfLotteryScheduler);
        srtfLotteryThread.start();

        for (Process ongoingProcess : ongoingProcesses) {
            srtfLotteryScheduler.addProcess(ongoingProcess);
            Thread.sleep(Timer.BURST_TIME/4);
        }

        srtfLotteryThread.join();
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

    private static List<Process> createOngoingProcesses() {
        List<Process> processes = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_ONGOING_PROCESSES; i++) {
            int processTime = ThreadLocalRandom.current().nextInt(MIN_PROCESS_LENGTH, MAX_PROCESS_LENGTH + 1);
            processes.add(
                    new Process(NUMBER_OF_SEED_PROCESSES + i, "Ongoing Process " + i, Timer.getCurrentTime(), processTime)
            );
        }

        return processes;
    }
}
