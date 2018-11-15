package com.stephengoeddel;

public class LotteryTicket {
    private final int number;

    public LotteryTicket(int number) {
        this.number = number;
    }

    public boolean isWinner(int number) {
        return this.number == number;
    }

    public int getNumber() {
        return number;
    }
}
