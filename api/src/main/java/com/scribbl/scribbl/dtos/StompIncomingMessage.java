package com.scribbl.scribbl.dtos;

import java.util.UUID;

public class StompIncomingMessage {

    private String content;

    private UUID roomId;

    private UUID userId;

    private String actionType;

    public String getContent() {
        return content;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getActionType() {
        return actionType;
    }
}
