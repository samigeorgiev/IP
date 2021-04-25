package com.scribbl.scribbl.dtos;

public class StompOutgoingMessage {

    private final String content;

    private final String actionType;

    public StompOutgoingMessage(String content, String actionType) {
        this.content = content;
        this.actionType = actionType;
    }

    public String getContent() {
        return content;
    }

    public String getActionType() {
        return actionType;
    }
}
