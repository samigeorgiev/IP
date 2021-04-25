package com.scribbl.scribbl.dtos;

import java.util.UUID;

public class RoomDto {

    private final UUID id;

    public RoomDto(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
