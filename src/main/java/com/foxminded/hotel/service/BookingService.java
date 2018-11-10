package com.foxminded.hotel.service;

import com.foxminded.hotel.model.User;
import com.foxminded.hotel.resources.BookingResource;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface BookingService {
    List<BookingResource> findAll(Optional<User> user);
    BookingResource findById(Long id);
    BookingResource newBooking(User user, Long roomId, LocalDate start, LocalDate end);
    BookingResource makeBooking(User user, Long roomId, LocalDate start, LocalDate end, long[] services);
    Resource calculateBookingPrice(Long roomId, LocalDate start, LocalDate end, long[] services);
}
