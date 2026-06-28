package org.example.lab5.services;

import org.example.lab5.domain.dtos.request.room.CreateRoomRequest;
import org.example.lab5.domain.dtos.response.room.RoomResponse;

import java.util.UUID;

public interface RoomService {

    org.example.lab5.domain.dtos.response.room.RoomResponse createRoom(CreateRoomRequest request);

    RoomResponse getRoomById(UUID id);

    RoomResponse getRoomByName(String name);
}
