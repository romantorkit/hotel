package com.foxminded.hotel.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.foxminded.hotel.controller.RoomController;
import com.foxminded.hotel.controller.UserController;
import com.foxminded.hotel.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
@Setter
public class UserResource extends ResourceSupport {
    private Long userId;

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime registered;

    @JsonCreator
    public UserResource() {
        this.userName = null;
        this.password = null;
        add(linkTo(methodOn(UserController.class).login(null, null, null)).withRel("login").withTitle("please login").withType("POST"));
    }

    @JsonCreator
    public UserResource(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.registered = user.getRegistered();
        add(linkTo(methodOn(UserController.class).userBookings(user.getUserId())).withRel("bookings").withMedia("application/json").withType("GET").withTitle("Check your bookings"));
        add(linkTo(methodOn(RoomController.class).rooms(null, null, null, null)).withRel("all_rooms").withMedia("application/json").withType("GET").withTitle("View all rooms"));
    }
}
