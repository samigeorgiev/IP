package com.scribbl.scribbl.dtos;

import java.util.List;

public class GetRoomsResponse {

    private final List<RoomDto> rooms;

    public GetRoomsResponse(List<RoomDto> rooms) {
        this.rooms = rooms;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }
}
