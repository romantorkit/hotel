package com.foxminded.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foxminded.hotel.enums.ChargePeriod;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class AdditionalService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;
    private String serviceName;
    private int price;
    @Enumerated(value = EnumType.STRING)
    private ChargePeriod chargePeriod;
    @JsonIgnore
    @ManyToMany(mappedBy = "services")
    private List<Booking> bookings = new ArrayList<>();
}
