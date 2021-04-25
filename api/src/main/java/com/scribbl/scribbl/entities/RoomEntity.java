package com.scribbl.scribbl.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomEntity {

    private final UUID id;

    private final List<UUID> users = new ArrayList<>();

    private UUID currentDrawer;

    private String canvasState;

    private List<String> possibleWords;

    private String word;

    public RoomEntity(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public List<UUID> getUsers() {
        return users;
    }

    public UUID getCurrentDrawer() {
        return currentDrawer;
    }

    public void setCurrentDrawer(UUID currentDrawer) {
        this.currentDrawer = currentDrawer;
    }

    public String getCanvasState() {
        return canvasState;
    }

    public void setCanvasState(String canvasState) {
        this.canvasState = canvasState;
    }

    public List<String> getPossibleWords() {
        return possibleWords;
    }

    public void setPossibleWords(List<String> possibleWords) {
        this.possibleWords = possibleWords;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
