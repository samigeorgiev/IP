package com.scribbl.scribbl.dtos;

public class StartGuessingMessage {

    private final int time;

    public StartGuessingMessage(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}
