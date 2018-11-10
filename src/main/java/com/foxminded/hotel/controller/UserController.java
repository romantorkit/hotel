package com.foxminded.hotel.controller;

import com.foxminded.hotel.model.User;
import com.foxminded.hotel.resources.UserResource;
import com.foxminded.hotel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@SessionAttributes("sessionUser")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> register(){
        Map<String, String> userRegister = new HashMap<>();
        userRegister.put("userName", null);
        userRegister.put("password", null);
        userRegister.put("firstName", null);
        userRegister.put("lastName", null);
        return ResponseEntity.ok(userRegister);
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> register(@RequestBody @Valid User user) {
        return ResponseEntity.status(201).body(userService.create(user));
    }

    @GetMapping(path = "/login")
    public ResponseEntity<?> login(){
        Map<String, String> userLogin = new HashMap<>();
        userLogin.put("userName", null);
        userLogin.put("password", null);
        return new ResponseEntity<>(userLogin, HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(HttpSession session,
                                              @RequestParam String userName,
                                              @RequestParam String password) {
        try {
            User user = userService.login(userName, password);
            session.setAttribute("sessionUser", user);
            return ResponseEntity.ok(new UserResource(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login");
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserResource> user(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping(path = "/{id}/bookings", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> userBookings(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getBookingsById(id));
    }
}
