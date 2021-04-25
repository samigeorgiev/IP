package com.scribbl.scribbl.dtos;

import java.util.UUID;

public class SetWordMessage {

    private UUID roomId;

    private String word;

    public UUID getRoomId() {
        return roomId;
    }

    public String getWord() {
        return word;
    }
}
