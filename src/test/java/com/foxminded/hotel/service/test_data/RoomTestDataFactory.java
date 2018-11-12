package com.foxminded.hotel.service.test_data;

import com.foxminded.hotel.enums.RoomCategory;
import com.foxminded.hotel.model.Room;
import com.foxminded.hotel.resources.RoomResource;
import org.assertj.core.util.Lists;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.foxminded.hotel.service.test_data.AdditionalServicesTestDataFactory.getAllServices;
import static com.foxminded.hotel.service.test_data.UserTestDataFactory.getUser1;

public class RoomTestDataFactory {

    public static Room getRoom1() {
        return new Room(1l, 1, RoomCategory.SINGLE, 1000);
    }

    public static RoomResource getRoomResource1() {
        return new RoomResource(getRoom1(), Optional.of(getUser1()), Optional.of(LocalDate.now()), Optional.of(LocalDate.now().plusDays(1L)), getAllServices());
    }

    public static Room getRoom2() {
        return new Room(2l, 2, RoomCategory.DOUBLE, 2000);
    }

    public static Room getRoom3() {
        return new Room(3l, 3, RoomCategory.FAMILY, 3000);
    }

    public static Room getRoom4() {
        return new Room(4l, 4, RoomCategory.PRESIDENT, 4000);
    }

    public static List<Room> getAllRooms() {
        return Lists.newArrayList(getRoom1(), getRoom2(), getRoom3(), getRoom4());
    }

    public static List<RoomResource> getAvailableRooms() {
        return Lists.newArrayList(getAllRooms()
                    .stream()
                    .map(room -> new RoomResource(room, Optional.of(getUser1()), Optional.of(LocalDate.now()), Optional.of(LocalDate.now().plusDays(1L)), getAllServices()))
                    .collect(Collectors.toList()));
        }

}
