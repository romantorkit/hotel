package com.foxminded.hotel.model;

import com.foxminded.hotel.enums.RoomCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private int roomNumber;
    @Enumerated(value = EnumType.STRING)
    private RoomCategory category;
    @NotNull
    private int price;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings = new ArrayList<>();
}
