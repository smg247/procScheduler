package com.stephengoeddel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LotteryScheduler extends Scheduler {
    private static final int NUM_LOTTERY_TICKETS = 50;

    private List<LotteryTicket> tickets = new ArrayList<>();
    private int nextLotteryTicketNumber;

    @Override
    protected Process determineNextProcessToRun() {
        int ticketNumber = drawTicketNumber();
        return processes.stream()
                .filter(process -> process.hasWinningTicket(ticketNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No process won..."));
    }

    @Override
    protected void internalAddProcess(Process process) {
        if (currentlyRunningProcess == null) {
            currentlyRunningProcess = process;
        }

        for (int i = 0; i < NUM_LOTTERY_TICKETS; i++) {
            LotteryTicket lotteryTicket = new LotteryTicket(nextLotteryTicketNumber + i);
            process.addLotteryTicket(lotteryTicket);
            tickets.add(lotteryTicket);
        }

        nextLotteryTicketNumber += NUM_LOTTERY_TICKETS;
    }

    private int drawTicketNumber() {
        return ThreadLocalRandom.current().nextInt(0, nextLotteryTicketNumber);
    }
}
