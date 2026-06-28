package org.example.lab5.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.lab5.common.mappers.RoomMapper;
import org.example.lab5.domain.dtos.request.room.CreateRoomRequest;
import org.example.lab5.domain.dtos.response.room.RoomResponse;
import org.example.lab5.domain.entities.Room;
import org.example.lab5.repositories.RoomRepository;
import org.example.lab5.services.RoomService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public RoomResponse createRoom(CreateRoomRequest request) {
        if (roomRepository.existsRoomsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException(
                    "Ya existe una sala con el nombre: " + request.getName()
            );
        }
        Room room = roomMapper.toEntityCreate(request);
        Room savedRoom = roomRepository.save(room);
        return roomMapper.toDto(savedRoom);
    }

    @Override
    public RoomResponse getRoomById(UUID id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale no encontrada con ID: " + id));
        return roomMapper.toDto(room);
    }

    @Override
    public RoomResponse getRoomByName(String name) {
        Room room = roomRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Sale no encontrada con nombre: " + name));
        return roomMapper.toDto(room);
    }
}
