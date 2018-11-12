package com.foxminded.hotel.service.test_data;

import com.foxminded.hotel.enums.RoomCategory;
import com.foxminded.hotel.model.Booking;
import com.foxminded.hotel.model.Room;
import com.foxminded.hotel.resources.RoomResource;
import org.assertj.core.util.Lists;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.foxminded.hotel.service.test_data.AdditionalServicesTestDataFactory.getAllServiceResource;
import static com.foxminded.hotel.service.test_data.BookingTestDataFactory.getAllBookings;
import static com.foxminded.hotel.service.test_data.UserTestDataFactory.getExistingUser;

public class RoomTestDataFactory {

    public static Room getRoom1() {
        return new Room(1l, 1, RoomCategory.SINGLE, 100000);
    }

    public static RoomResource getRoomResource1() {
        return new RoomResource(getRoom1(), Optional.of(getExistingUser()), Optional.of(LocalDate.now()), Optional.of(LocalDate.now().plusDays(1L)), getAllServiceResource());
    }

    public static Room getRoom2() {
        return new Room(2l, 2, RoomCategory.DOUBLE, 200000);
    }

    public static Room getRoom3() {
        return new Room(3l, 3, RoomCategory.FAMILY, 300000);
    }

    public static Room getRoom4() {
        return new Room(4l, 4, RoomCategory.PRESIDENT, 400000);
    }

    public static List<Room> getAllRooms() {
        return Lists.newArrayList(getRoom1(), getRoom2(), getRoom3(), getRoom4());
    }

    public static List<RoomResource> getAvailableRooms(LocalDate start, LocalDate end) {
        return Lists.newArrayList(getAllRooms()
                    .stream()
                    .peek(room -> room.setBookings(filterBookings(room)))
                    .flatMap(room -> room.getBookings().stream())
                    .filter(booking -> !(start.isAfter(booking.getStart()) && start.isBefore(booking.getEnd())) ||
                        !(end.isAfter(booking.getStart()) && end.isBefore(booking.getEnd())) ||
                        !(start.isBefore(booking.getStart()) && end.isAfter(booking.getEnd())))
                    .map(Booking::getRoom)
                    .map(room -> new RoomResource(room, Optional.of(getExistingUser()), Optional.of(LocalDate.now()), Optional.of(LocalDate.now().plusDays(1L)), getAllServiceResource()))
                    .collect(Collectors.toList()));
        }

        private static List<Booking> filterBookings(Room room){
            return getAllBookings()
                    .stream()
                    .filter(booking -> booking.getRoom().equals(room))
                    .collect(Collectors.toList());
        }

}
