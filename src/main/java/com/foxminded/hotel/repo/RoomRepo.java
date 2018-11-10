package com.foxminded.hotel.repo;

import com.foxminded.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoomRepo extends JpaRepository<Room, Long> {
}
