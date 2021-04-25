package com.scribbl.scribbl.controllers;

import com.scribbl.scribbl.dtos.GetRoomsResponse;
import com.scribbl.scribbl.dtos.RoomDto;
import com.scribbl.scribbl.services.RoomsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomsController {

    private final RoomsService roomsService;

    public RoomsController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @GetMapping
    public GetRoomsResponse getRooms() {
        List<RoomDto> rooms = roomsService.getRooms();
        return new GetRoomsResponse(rooms);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createRoom() {
        roomsService.createRoom();
    }
}

