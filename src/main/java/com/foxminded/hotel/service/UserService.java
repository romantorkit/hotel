package com.foxminded.hotel.service;

import com.foxminded.hotel.model.User;
import com.foxminded.hotel.resources.BookingResource;
import com.foxminded.hotel.resources.UserResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User login(String username, String password) throws Exception;
    UserResource getById(Long id);
    List<BookingResource> getBookingsById(Long id);
    UserResource create(User user);
}
