package com.foxminded.hotel.controller;

import com.foxminded.hotel.model.User;
import com.foxminded.hotel.resources.RoomResource;
import com.foxminded.hotel.service.RoomService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
@SessionAttributes("sessionUser")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomResource>> rooms(HttpSession session,
                                                    @RequestParam(name = "category", required = false) Optional<String> category,
                                                    @RequestParam (name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                    @RequestParam (name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end){
        Optional<User> user = Optional.ofNullable((User)session.getAttribute("sessionUser"));
        return ResponseEntity.ok(roomService.findAvailableRooms(user, category, start, end));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoomResource> room(HttpSession session,
                                             @PathVariable Long id,
                                             @RequestParam (name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                             @RequestParam (name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        Optional<User> user = Optional.ofNullable((User)session.getAttribute("sessionUser"));
        return ResponseEntity.ok(roomService.findRoom(user, id, start, end));
    }
}
