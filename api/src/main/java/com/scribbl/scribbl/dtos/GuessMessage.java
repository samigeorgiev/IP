package com.scribbl.scribbl.dtos;

import java.util.UUID;

public class GuessMessage {

    private UUID roomId;

    private String guess;

    private UUID userId;

    public UUID getRoomId() {
        return roomId;
    }

    public String getGuess() {
        return guess;
    }

    public UUID getUserId() {
        return userId;
    }
}
