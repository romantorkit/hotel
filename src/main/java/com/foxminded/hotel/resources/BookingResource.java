package com.foxminded.hotel.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.foxminded.hotel.controller.BookingController;
import com.foxminded.hotel.model.AdditionalService;
import com.foxminded.hotel.model.Booking;
import com.foxminded.hotel.model.Room;
import com.foxminded.hotel.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
@Setter
@NoArgsConstructor
public class BookingResource extends ResourceSupport {
    private Long bookingId;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate start;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate end;
    private int amount;
    private RoomResource room;
    private List<ServiceResource> services;
    private User user;

    @JsonCreator
    public BookingResource(Booking booking) {
        this.bookingId = booking.getBookingId();
        this.start = booking.getStart();
        this.end = booking.getEnd();
        this.amount = booking.getAmount()/100;
        this.services = booking.getServices().stream().map(ServiceResource::new).collect(Collectors.toList());
        this.user = booking.getUser();
        this.room = new RoomResource(booking.getRoom(), Optional.ofNullable(booking.getUser()), Optional.ofNullable(start), Optional.ofNullable(end), services);
        long[] serviceIdArray = booking.getServices()
                .stream()
                .map(AdditionalService::getServiceId)
                .mapToLong(Long::longValue)
                .toArray();

        add(linkTo(methodOn(BookingController.class).create(null, booking.getStart(), booking.getEnd(), room.getRoomId(), serviceIdArray)).withRel("confirm_booking").withTitle("Confirm booking").withType("POST").withMedia("application/json"));

//        add(linkTo(methodOn(BookingController.class).booking(booking.getBookingId())).withSelfRel().withType("GET").withMedia("application/json"));
    }

    @JsonCreator
    public BookingResource(Booking booking, Optional<User> user, Room room, LocalDate start, LocalDate end, List<ServiceResource> services) {
        this.bookingId = booking.getBookingId();
        this.start = start;
        this.end = end;
        this.amount = booking.getAmount()/100;
        this.room = new RoomResource(room, user, Optional.ofNullable(start), Optional.ofNullable(end), services);

        if(user.isPresent()){
            this.user = user.get();
            long[] serviceIdArray = booking.getServices()
                    .stream()
                    .map(AdditionalService::getServiceId)
                    .mapToLong(Long::longValue)
                    .toArray();
            add(linkTo(methodOn(BookingController.class).create(null, start, end, room.getRoomId(), serviceIdArray)).withRel("confirm_booking").withTitle("Confirm booking").withType("POST").withMedia("application/json"));
            this.services = booking.getServices().stream().map(ServiceResource::new).collect(Collectors.toList());
        } else {
            this.services = services;
        }

        add(linkTo(methodOn(BookingController.class).booking(booking.getBookingId())).withSelfRel().withType("GET").withMedia("application/json"));
    }
}
