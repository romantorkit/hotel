package com.foxminded.hotel.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.foxminded.hotel.controller.BookingController;
import com.foxminded.hotel.controller.RoomController;
import com.foxminded.hotel.controller.UserController;
import com.foxminded.hotel.enums.RoomCategory;
import com.foxminded.hotel.model.AdditionalService;
import com.foxminded.hotel.model.Booking;
import com.foxminded.hotel.model.Room;
import com.foxminded.hotel.model.User;
import com.foxminded.hotel.repo.AdditionalServiceRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.ResourceSupport;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
@Setter
@NoArgsConstructor
public class RoomResource extends ResourceSupport {

    private Long roomId;
    private int roomNumber;
    private RoomCategory category;
    private int price;
//    private List<BookingResource> bookings;
    private List<ServiceResource> services;

    @JsonCreator
    public RoomResource(Room room, Optional<User> user, Optional<LocalDate> start, Optional<LocalDate> end, List<ServiceResource> services) {
        this.roomId = room.getRoomId();
        this.roomNumber = room.getRoomNumber();
        this.category = room.getCategory();
        this.price = setBookingPrice(start.orElse(LocalDate.now()), end.orElse(LocalDate.now().plusDays(1L)), room.getPrice()/100);
        this.services = services;

        if(user.isPresent()){
            add(linkTo(methodOn(BookingController.class).newBooking(null, start.orElse(LocalDate.now()), end.orElse(LocalDate.now().plusDays(1L)), room.getRoomId())).withRel("book_room").withMedia("application/json").withType("GET").withTitle("Book the room"));
            add(linkTo(methodOn(BookingController.class).price(start.orElse(LocalDate.now()), end.orElse(LocalDate.now().plusDays(1L)), room.getRoomId(), new long[services.size()])).withRel("calculate_price").withTitle("Calculate booking price"));
        } else {
            add(linkTo(methodOn(UserController.class).register()).withRel("register").withType("GET").withTitle("Please register"));
            add(linkTo(methodOn(UserController.class).login()).withRel("login").withType("GET").withTitle("Please login"));
        }

        add(linkTo(methodOn(RoomController.class).room(null, room.getRoomId(), start.orElse(LocalDate.now()), end.orElse(LocalDate.now().plusDays(1L)))).withSelfRel().withMedia("application/json").withType("GET").withTitle("View room information"));

    }

    private int setBookingPrice(LocalDate start, LocalDate end, int price) {
        return (int)Duration.between(start.atStartOfDay(), end.atTime(23, 59, 59)).toHours() / 24 * price;
    }
}
