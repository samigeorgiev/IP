package com.scribbl.scribbl.dtos;

import java.util.UUID;

public class SetCanvasMessage {

    private UUID roomId;

    private String canvas;

    public UUID getRoomId() {
        return roomId;
    }

    public String getCanvas() {
        return canvas;
    }
}
