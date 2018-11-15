package com.stephengoeddel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractLotteryScheduler extends Scheduler {

    protected List<LotteryTicket> tickets = new ArrayList<>();
    protected int nextLotteryTicketNumber;

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

        distributeLotteryTickets(process);
    }

    @Override
    protected void notifyOfFinishedProcess(Process process) {
        for (LotteryTicket ticket : process.getTickets()) {
            tickets.remove(ticket);
        }

        process.clearTickets();
    }

    private int drawTicketNumber() {
        int ticketIndex = ThreadLocalRandom.current().nextInt(0, tickets.size());
        return tickets.get(ticketIndex).getNumber();
    }

    protected abstract void distributeLotteryTickets(Process process);
}
