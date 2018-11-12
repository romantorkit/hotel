package com.foxminded.hotel.service.test_data;

import com.foxminded.hotel.model.Booking;
import com.foxminded.hotel.model.User;
import com.foxminded.hotel.resources.BookingResource;
import org.assertj.core.util.Lists;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.foxminded.hotel.service.test_data.AdditionalServicesTestDataFactory.*;
import static com.foxminded.hotel.service.test_data.RoomTestDataFactory.getRoom1;
import static com.foxminded.hotel.service.test_data.RoomTestDataFactory.getRoom2;
import static com.foxminded.hotel.service.test_data.UserTestDataFactory.getExistingUser;

public class BookingTestDataFactory {

    public static Booking getBooking1(){
        Booking booking = new Booking(LocalDate.now(),
                LocalDate.now().plusDays(1L),
                125000, getRoom1(), getExistingUser());
        booking.setServices(getServices(getService1(), getService2()));
        return booking;
    }

    public static BookingResource getBookingResource1(){
        return new BookingResource(getBooking1());
    }

    public static Booking getBooking2(){
        Booking booking = new Booking(LocalDate.now().plusDays(1L),
                LocalDate.now().plusDays(2L),
                200000, getRoom2(), getExistingUser());
        booking.setServices(getServices(getService1(), getService3()));
        return booking;
    }

    public static BookingResource getBookingResource2(){
        return new BookingResource(getBooking2());
    }

    public static List<Booking> getBookingList(Booking... booking){
        return Lists.newArrayList(booking);
    }

    public static List<Booking> getAllBookings(){
        return Lists.newArrayList(getBooking1(), getBooking2());
    }

    public static List<Booking> getAllBookingsByUser(User user){
        return Lists.newArrayList(getBooking1(), getBooking2());
    }

    public static List<Booking> findBookingsByPeriod(LocalDate start, LocalDate end) {
        return Lists.newArrayList(getBooking1(), getBooking2())
                .stream()
                .filter(booking -> !(start.isAfter(booking.getStart()) && start.isBefore(booking.getEnd())) ||
                !(end.isAfter(booking.getStart()) && end.isBefore(booking.getEnd())) ||
                !(start.isBefore(booking.getStart()) && end.isAfter(booking.getEnd())))
                .collect(Collectors.toList());
    }

    public static List<BookingResource> getBookingResourceListByUser(User user){
        return Lists.newArrayList(getBookingResource1(), getBookingResource2())
                .stream()
                .filter(bookingResource -> bookingResource.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public static List<BookingResource> getBookingResourceList(){
        return Lists.newArrayList(getBookingResource1(), getBookingResource2());
    }
}
