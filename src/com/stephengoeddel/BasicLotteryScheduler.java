package com.stephengoeddel;

public class BasicLotteryScheduler extends AbstractLotteryScheduler {
    private static final int NUM_LOTTERY_TICKETS = 50;

    @Override
    protected void distributeLotteryTickets(Process process) {
        for (int i = 0; i < NUM_LOTTERY_TICKETS; i++) {
            LotteryTicket lotteryTicket = new LotteryTicket(nextLotteryTicketNumber + i);
            process.addLotteryTicket(lotteryTicket);
            tickets.add(lotteryTicket);
        }

        nextLotteryTicketNumber += NUM_LOTTERY_TICKETS;
    }
}
