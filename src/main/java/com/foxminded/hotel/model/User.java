package com.foxminded.hotel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotEmpty
    @Size(min=4, max=20)
    @Column(length = 20, nullable = false)
    @NonNull private String userName;
    @NotEmpty
    @Size(min=4, max=120)
    @Column(length = 120, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NonNull private String password;
    @Size(min=5, max=12)
    @NonNull private String firstName;
    @Size(min=5, max=12)
    @NonNull private String lastName;
    private LocalDateTime registered;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();
}
