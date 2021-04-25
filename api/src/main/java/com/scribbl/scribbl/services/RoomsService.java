package com.scribbl.scribbl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scribbl.scribbl.dtos.*;
import com.scribbl.scribbl.entities.RoomEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class RoomsService {

    private final SimpMessagingTemplate messagingTemplate;

    private final Map<UUID, RoomEntity> rooms = new ConcurrentHashMap<>();

    private final List<String> dictionary = new ArrayList<>() {{
        add("test1");
        add("test2");
        add("test3");
        add("test4");
        add("test5");
        add("test6");
        add("test7");
        add("test8");
        add("test9");
        add("test10");
        add("test11");
    }};

    public RoomsService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public List<RoomDto> getRooms() {
        return rooms
                .values()
                .stream()
                .map(x -> new RoomDto(x.getId()))
                .collect(Collectors.toList());
    }

    public void createRoom() {
        UUID id = UUID.randomUUID();
        rooms.put(id, new RoomEntity(id));
    }

    public void registerUser(RegisterMessage message) {
        RoomEntity room = rooms.get(message.getRoomId());
        UUID userId = message.getUserId();
        List<UUID> roomsUsers = room.getUsers();
        if (roomsUsers.contains(userId)) {
            return;
        }
        roomsUsers.add(userId);
        if (roomsUsers.size() == 1) {
            setDrawer(room, userId);
        }
    }

    public void setCanvas(SetCanvasMessage message) {
        RoomEntity room = rooms.get(message.getRoomId());
        room.setCanvasState(message.getCanvas());
        messagingTemplate.convertAndSendToUser(room.getId().toString(), "update-canvas", new UpdateCanvasMessage(room.getCanvasState()));
    }

    public void setWord(SetWordMessage message) {
        RoomEntity room = rooms.get(message.getRoomId());
        String word = message.getWord();
        if (!room.getPossibleWords().contains(word)) {
            return;
        }
        room.setWord(word);
        sendMessageWithDelay(room.getId().toString(), "start-guessing", new StartGuessingMessage(30));
    }

    public void guess(GuessMessage message) {
        RoomEntity room = rooms.get(message.getRoomId());
        String displayMessage;
        if (room.getWord().equals(message.getGuess())) {
            displayMessage = message.getUserId() + " guessed the word";
        } else {
            displayMessage = message.getUserId() + ": " + message.getGuess();
        }
        sendMessageWithDelay(room.getId().toString(), "messages", new MessagesMessage(displayMessage));
    }

    private void setDrawer(RoomEntity room, UUID userId) {
        room.setCurrentDrawer(userId);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://random-word-api.herokuapp.com/word?number=4"))
                .GET()
                .build();
        String[] words = new String[0];
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            words = new ObjectMapper().readValue(response.body(), String[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        room.setPossibleWords(List.of(words));
        messagingTemplate.convertAndSendToUser(room.getId().toString(), "set-drawer", new SetDrawerMessage(userId, room.getPossibleWords()));
    }

    private void sendMessageWithDelay(String user, String destination, Object payload) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        messagingTemplate.convertAndSendToUser(user, destination, payload);
    }
}
