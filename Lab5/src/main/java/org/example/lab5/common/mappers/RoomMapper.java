package org.example.lab5.common.mappers;

import org.example.lab5.domain.dtos.request.room.CreateRoomRequest;
import org.example.lab5.domain.dtos.response.room.RoomResponse;
import org.example.lab5.domain.entities.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public Room toEntityCreate(CreateRoomRequest createRoomRequest) {
        return Room.builder()
                .name(createRoomRequest.getName())
                .description(createRoomRequest.getDescription())
                .capacity(createRoomRequest.getCapacity())
                .location(createRoomRequest.getLocation())
                .active(createRoomRequest.getActive())
                .build();
    }

    public RoomResponse toDto(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .description(room.getDescription())
                .capacity(room.getCapacity())
                .location(room.getLocation())
                .active(room.getActive())
                .build();
    }

    public Room toEntity(RoomResponse roomResponse) {
        return Room.builder()
                .id(roomResponse.getId())
                .name(roomResponse.getName())
                .description(roomResponse.getDescription())
                .capacity(roomResponse.getCapacity())
                .location(roomResponse.getLocation())
                .active(roomResponse.getActive())
                .build();
    }
}
