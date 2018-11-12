package com.foxminded.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @Future
    @NonNull private LocalDate start;
    @Future
    @NonNull private LocalDate end;
    @NonNull private int amount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @NonNull private Room room;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull private User user;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "booking_additional_service", joinColumns = {
            @JoinColumn(name = "booking_id", nullable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "service_id", nullable = false)
    })
    private List<AdditionalService> services = new ArrayList<>();
}
