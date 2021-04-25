package com.scribbl.scribbl.dtos;

import java.util.UUID;

public class RegisterMessage {

    private UUID roomId;

    private UUID userId;

    public UUID getRoomId() {
        return roomId;
    }

    public UUID getUserId() {
        return userId;
    }
}
