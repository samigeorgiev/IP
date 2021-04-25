package com.scribbl.scribbl.controllers;

import com.scribbl.scribbl.dtos.*;
import com.scribbl.scribbl.services.DrawingService;
import com.scribbl.scribbl.services.RoomsService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class StompController {

    private final RoomsService roomsService;

    public StompController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @MessageMapping("/register")
    public void register(@Payload RegisterMessage message) {
        roomsService.registerUser(message);
    }

    @MessageMapping("/set-canvas")
    public void setCanvas(@Payload SetCanvasMessage message) {
        roomsService.setCanvas(message);
    }

    @MessageMapping("/set-word")
    public void setWord(@Payload SetWordMessage message) {
        roomsService.setWord(message);
    }

    @MessageMapping("/guess")
    public void guess(@Payload GuessMessage message) {
        roomsService.guess(message);
    }
}
