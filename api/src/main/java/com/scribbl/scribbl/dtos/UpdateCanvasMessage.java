package com.scribbl.scribbl.dtos;

public class UpdateCanvasMessage {

    private final String canvas;

    public UpdateCanvasMessage(String canvas) {
        this.canvas = canvas;
    }

    public String getCanvas() {
        return canvas;
    }
}
