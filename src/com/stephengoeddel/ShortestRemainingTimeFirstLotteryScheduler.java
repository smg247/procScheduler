package com.stephengoeddel;

import java.util.Collections;
import java.util.Comparator;

public class ShortestRemainingTimeFirstLotteryScheduler extends AbstractLotteryScheduler {
    private static final int ORDER_MULTIPLIER = 2;

    @Override
    protected void distributeLotteryTickets(Process process) {
        // Ordered by shortest time remaining LAST
        processes.sort(Comparator.comparingInt(o -> (int) o.getTimeRemaining()));
        Collections.reverse(processes);

        // All new tickets will be issued
        tickets.clear();

        for (int i = 0; i < processes.size(); i++) {
            Process existingProcess = processes.get(i);
            existingProcess.clearTickets();
            int numberOfTickets = (i + 1) * ORDER_MULTIPLIER;
            for (int j = 0; j < numberOfTickets; j++) {
                LotteryTicket ticket = new LotteryTicket(nextLotteryTicketNumber);
                existingProcess.addLotteryTicket(ticket);
                tickets.add(ticket);
                nextLotteryTicketNumber++;
            }
        }
    }
}
