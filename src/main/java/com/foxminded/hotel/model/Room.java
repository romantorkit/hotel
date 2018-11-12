package com.foxminded.hotel.model;

import com.foxminded.hotel.enums.RoomCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull private Long roomId;
    @NonNull private int roomNumber;
    @Enumerated(value = EnumType.STRING)
    @NonNull private RoomCategory category;
    @NotNull
    @NonNull private int price;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings = new ArrayList<>();
}
