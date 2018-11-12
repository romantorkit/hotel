package com.foxminded.hotel.service.test_data;

import com.foxminded.hotel.model.User;
import com.foxminded.hotel.resources.UserResource;

public class UserTestDataFactory {

    public static User getExistingUser() {
        User user = new User("tort", "1234", "Roman", "Torkit");
        user.setUserId(1L);
        return user;
    }

    public static UserResource getExistingUserResource() {
        return new UserResource(getExistingUser());
    }

    public static User getUserToLogin() {
        return new User("tort", "$2a$10$fkdCewu6ob/KEHnmeRHGpegT3UQDHet3.uflaXtk7LQlUjpZgNZYa", "Roman", "Torkit");
    }

    public static User getUserToRegister() {
        return new User("torkit", "$2a$10$fkdCewu6ob/KEHnmeRHGpegT3UQDHet3.uflaXtk7LQlUjpZgNZYa", "Roman", "Torkit");
    }

    public static UserResource getUserToRegisterResource() {
        return new UserResource(getUserToRegister());
    }
}
