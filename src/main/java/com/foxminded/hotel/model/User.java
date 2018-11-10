package com.foxminded.hotel.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotEmpty
    @Size(min=4, max=12)
    private String userName;
    @NotEmpty
    @Size(min=4, max=12)
    private String password;
    @Size(min=5, max=12)
    private String firstName;
    @Size(min=5, max=12)
    private String lastName;
    private LocalDateTime registered;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();
}
