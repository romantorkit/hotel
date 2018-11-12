package com.foxminded.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foxminded.hotel.enums.ChargePeriod;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class AdditionalService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;
    @NonNull private String serviceName;
    @NonNull private int price;
    @Enumerated(value = EnumType.STRING)
    @NonNull private ChargePeriod chargePeriod;
    @JsonIgnore
    @ManyToMany(mappedBy = "services")
    private List<Booking> bookings = new ArrayList<>();
}
