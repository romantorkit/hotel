package com.foxminded.hotel.service;

import com.foxminded.hotel.enums.RoomCategory;
import com.foxminded.hotel.exception_handling.EntityNotFoundException;
import com.foxminded.hotel.model.Booking;
import com.foxminded.hotel.model.Room;
import com.foxminded.hotel.model.User;
import com.foxminded.hotel.repo.AdditionalServiceRepo;
import com.foxminded.hotel.repo.BookingRepo;
import com.foxminded.hotel.repo.RoomRepo;
import com.foxminded.hotel.resources.RoomResource;
import com.foxminded.hotel.resources.ServiceResource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class RoomServiceImpl implements RoomService {

    private final RoomRepo roomRepo;
    private final BookingRepo bookingRepo;
    private final AdditionalServiceRepo serviceRepo;

    public RoomServiceImpl(RoomRepo roomRepo,
                           BookingRepo bookingRepo,
                           AdditionalServiceRepo serviceRepo) {
        this.roomRepo = roomRepo;
        this.bookingRepo = bookingRepo;
        this.serviceRepo = serviceRepo;
    }

    @Override
    public List<RoomResource> findAvailableRooms(Optional<User> user, Optional<String> optionalCategory, LocalDate start, LocalDate end) {
        List<ServiceResource> services = serviceRepo.findAll().stream()
                .map(ServiceResource::new)
                .collect(Collectors.toList());
        List<Room> bookedRooms = bookingRepo.findBookingsByPeriod(start, end)
                .stream()
                .map(Booking::getRoom)
                .collect(Collectors.toList());
        AtomicReference<List<Room>> availableRooms = new AtomicReference<>(roomRepo.findAll());
        optionalCategory.ifPresent(category -> {
            availableRooms.set(availableRooms.get().stream()
                    .filter(room -> room.getCategory().equals(RoomCategory.valueOf(category.toUpperCase())))
                    .collect(Collectors.toList()));
                }
        );
        availableRooms.get().removeAll(bookedRooms);
        return availableRooms.get()
                .stream()
                .map(room -> new RoomResource(room, user, Optional.ofNullable(start), Optional.ofNullable(end), services))
                .collect(Collectors.toList());
    }

    @Override
    public RoomResource findRoom(Optional<User> user, Long roomId, LocalDate start, LocalDate end) {
        List<ServiceResource> services = serviceRepo.findAll().stream()
                .map(ServiceResource::new)
                .collect(Collectors.toList());
        Room room = roomRepo.findById(roomId).orElseThrow(() -> new EntityNotFoundException(Room.class, "roomId", String.valueOf(roomId)));
        return new RoomResource(room, user, Optional.ofNullable(start), Optional.ofNullable(end), services);
    }
}
