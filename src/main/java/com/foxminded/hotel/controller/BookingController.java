package com.foxminded.hotel.controller;

import com.foxminded.hotel.model.User;
import com.foxminded.hotel.resources.UserResource;
import com.foxminded.hotel.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Future;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
@SessionAttributes("sessionUser")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> bookings(HttpSession session) {
        Optional<User> user = Optional.ofNullable((User) session.getAttribute("sessionUser"));
        return ResponseEntity.ok(bookingService.findAll(user));
    }

    @GetMapping(value = "/new", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> newBooking(HttpSession session,
                                        @RequestParam ("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Future(message = "PLease choose a correct start date") LocalDate start,
                                        @RequestParam ("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Future(message = "PLease choose a correct end date") LocalDate end,
                                        @RequestParam Long roomId) {
        Optional<User> optionalUser = Optional.ofNullable((User) session.getAttribute("sessionUser"));
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(bookingService.newBooking(optionalUser.get(), roomId, start, end));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new UserResource());
        }

    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> booking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> create(HttpSession session,
                                    @RequestParam ("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Future LocalDate start,
                                    @RequestParam ("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Future LocalDate end,
                                    @RequestParam ("roomId") Long roomId,
                                    @RequestParam ("services") long[] services) {
        Optional<User> user = Optional.ofNullable((User)session.getAttribute("sessionUser"));
        if (user.isPresent()) {
            return ResponseEntity.status(201).body(bookingService.makeBooking(user.get(), roomId, start, end, services));
        } else {
            return ResponseEntity.ok(new UserResource());
        }
    }

    @GetMapping(path = "/price", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> price(@RequestParam ("start") @Future(message = "PLease choose a correct start date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                   @RequestParam ("end") @Future(message = "PLease choose a correct end date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                                   @RequestParam ("roomId") Long roomId,
                                   @RequestParam ("services") long[] services) {

        Resource priceResource = bookingService.calculateBookingPrice(roomId, start, end, services);
        return new ResponseEntity<>(priceResource, HttpStatus.OK);
    }


}
