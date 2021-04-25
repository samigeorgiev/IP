package com.scribbl.scribbl.dtos;

import java.util.List;
import java.util.UUID;

public class SetDrawerMessage {

    private final UUID userId;

    private final List<String> possibleWords;

    public SetDrawerMessage(UUID userId, List<String> possibleWords) {
        this.userId = userId;
        this.possibleWords = possibleWords;
    }

    public UUID getUserId() {
        return userId;
    }

    public List<String> getPossibleWords() {
        return possibleWords;
    }
}
