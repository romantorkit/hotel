package com.foxminded.hotel.service;

import com.foxminded.hotel.exception_handling.DuplicateEntryException;
import com.foxminded.hotel.exception_handling.EntityNotFoundException;
import com.foxminded.hotel.model.AdditionalService;
import com.foxminded.hotel.model.User;
import com.foxminded.hotel.repo.UserRepo;
import com.foxminded.hotel.resources.BookingResource;
import com.foxminded.hotel.resources.ServiceResource;
import com.foxminded.hotel.resources.UserResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public User login(String username, String password) throws Exception{
        User user = userRepo.findByUserName(username);
        if (user.getUserName().equals(username) && encoder.matches(password, user.getPassword())){
            return user;
        } else {
            throw new Exception();
        }
    }

    @Override
    public UserResource getById(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class, "userId", String.valueOf(userId)));
        return new UserResource(user);
    }

    @Override
    public List<BookingResource> getBookingsById(Long id) {
        return userRepo.getOne(id).getBookings()
                .stream()
                .map(booking -> new BookingResource(booking, Optional.ofNullable(booking.getUser()), booking.getRoom(), booking.getStart(), booking.getEnd(), convertToServiceResourceList(booking.getServices())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResource create(User user) {

        if(userRepo.findByUserName(user.getUserName()) == null){
            user.setRegistered(LocalDateTime.now());
            user.setPassword(encoder.encode(user.getPassword()));
            user = userRepo.save(user);
            return new UserResource(user);
        } else {
            throw new DuplicateEntryException(User.class, "user", user.getUserName());
        }

    }

    private List<ServiceResource> convertToServiceResourceList(List<AdditionalService> services){
        return services.stream().map(ServiceResource::new).collect(Collectors.toList());
    }
}
