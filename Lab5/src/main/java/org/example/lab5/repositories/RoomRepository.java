package org.example.lab5.repositories;

import org.example.lab5.domain.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Optional<Room> findByNameIgnoreCase(String name);
    boolean existsRoomsByNameIgnoreCase(String name);
}
