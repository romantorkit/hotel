package com.foxminded.hotel.service;

import com.foxminded.hotel.model.User;
import com.foxminded.hotel.resources.RoomResource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface RoomService {
    List<RoomResource> findAvailableRooms(Optional<User> user, Optional<String> category, LocalDate start, LocalDate end);
    RoomResource findRoom(Optional<User> user, Long id, LocalDate start, LocalDate end);
}
