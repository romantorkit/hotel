package com.foxminded.hotel.repo;

import com.foxminded.hotel.model.Booking;
import com.foxminded.hotel.model.Room;
import com.foxminded.hotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface BookingRepo extends JpaRepository<Booking, Long> {
    @Query("select b from Booking b where (?1 between b.start and b.end) or (?2 between b.start and b.end) or (?1 <= b.start and ?2 >= b.end)")
    List<Booking> findBookingsByPeriod(LocalDate start, LocalDate end);

    boolean existsByRoomAndUserAndStartAndEnd(Room room, User user, LocalDate start, LocalDate end);
}
