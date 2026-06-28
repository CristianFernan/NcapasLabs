package org.example.lab5.services.impl;

import org.example.lab5.common.mappers.RoomMapper;
import org.example.lab5.domain.dtos.request.room.CreateRoomRequest;
import org.example.lab5.domain.dtos.response.room.RoomResponse;
import org.example.lab5.domain.entities.Room;
import org.example.lab5.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImpTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomMapper roomMapper;

    @InjectMocks
    private RoomServiceImpl roomService;

    private UUID roomId;
    private CreateRoomRequest request;
    private Room roomEntity;
    private RoomResponse roomResponse;

    @BeforeEach
    void setUp() {
        roomId = UUID.randomUUID();

        request = CreateRoomRequest.builder()
                .name("Auditorio Principal")
                .description("Auditorio principal para eventos académicos")
                .capacity(200)
                .location("Edificio A, Planta Baja")
                .build();

        roomEntity = Room.builder()
                .id(roomId)
                .name(request.getName())
                .description(request.getDescription())
                .capacity(request.getCapacity())
                .location(request.getLocation())
                .active(true)
                .build();

        roomResponse = RoomResponse.builder()
                .id(roomId)
                .name(roomEntity.getName())
                .description(roomEntity.getDescription())
                .capacity(roomEntity.getCapacity())
                .location(roomEntity.getLocation())
                .active(roomEntity.getActive())
                .build();
    }

    // Tests
    @Test
    void createRoom_shouldSaveAndReturnRoom_whenNameIsUnique() {
        // Arrange
        when(roomRepository.existsRoomsByNameIgnoreCase(request.getName())).thenReturn(false);
        when(roomMapper.toEntityCreate(request)).thenReturn(roomEntity);
        when(roomRepository.save(roomEntity)).thenReturn(roomEntity);
        when(roomMapper.toDto(roomEntity)).thenReturn(roomResponse);

        // Act
        RoomResponse result = roomService.createRoom(request);

        // Assert
        assertThat(result).isEqualTo(roomResponse);
        assertThat(result.getName()).isEqualTo("Auditorio Principal");
        assertThat(result.getCapacity()).isEqualTo(200);
        assertThat(result.getActive()).isTrue();

        // Verify
        verify(roomRepository, times(1)).save(roomEntity);
    }

    @Test
    void createRoom_shouldThrowException_whenNameAlreadyExists() {
        // Arrange El nombre ya existe
        when(roomRepository.existsRoomsByNameIgnoreCase(request.getName())).thenReturn(true);
        assertThatThrownBy(() -> roomService.createRoom(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe una sala con el nombre");

        // Verificar que nunca se intento guardar el dato
        verify(roomRepository, never()).save(any());
    }

    @Test
    void createRoom_shouldSaveRoomWithActiveTrue_byDefault() {
        when(roomRepository.existsRoomsByNameIgnoreCase(request.getName())).thenReturn(false);
        when(roomMapper.toEntityCreate(request)).thenReturn(roomEntity);
        when(roomRepository.save(roomEntity)).thenReturn(roomEntity);
        when(roomMapper.toDto(roomEntity)).thenReturn(roomResponse);

        RoomResponse result = roomService.createRoom(request);

        assertThat(result.getActive()).isTrue();
    }

    @Test
    void getRoomById_shouldReturnRoom_whenRoomExists() {
        // Arrange
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(roomEntity));
        when(roomMapper.toDto(roomEntity)).thenReturn(roomResponse);
        // Act
        RoomResponse result = roomService.getRoomById(roomId);
        // Asserts
        assertThat(result).isEqualTo(roomResponse);
        assertThat(result.getId()).isEqualTo(roomId);
    }

    @Test
    void getRoomById_shouldThrowException_whenRoomNotFound() {
        // Arrange
        UUID nonExistentRoomId = UUID.randomUUID();
        when(roomRepository.findById(nonExistentRoomId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> roomService.getRoomById(nonExistentRoomId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Sale no encontrada con ID");
    }

    @Test
    void getRoomByName_shouldReturnRoom_whenRoomExists() {
        String name = roomEntity.getName();
        when(roomRepository.findByNameIgnoreCase(name)).thenReturn(Optional.of(roomEntity));
        when(roomMapper.toDto(roomEntity)).thenReturn(roomResponse);

        RoomResponse result = roomService.getRoomByName(name);

        assertThat(result).isEqualTo(roomResponse);
        assertThat(result.getName()).isEqualTo(name);
    }


    @Test
    void getRoomByName_shouldThrowException_whenNameNotFound() {
        String nonExistentName = "Sala fantasma";
        when(roomRepository.findByNameIgnoreCase(nonExistentName)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> roomService.getRoomByName(nonExistentName))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Sale no encontrada con nombre");
    }
}
