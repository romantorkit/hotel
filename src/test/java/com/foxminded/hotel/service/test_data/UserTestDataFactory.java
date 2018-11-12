package com.foxminded.hotel.service.test_data;

import com.foxminded.hotel.model.User;
import com.foxminded.hotel.resources.UserResource;

public class UserTestDataFactory {

    public static User getUser1() {
        User user = new User("tort", "1234", "Roman", "Torkit");
        user.setUserId(1L);
        return user;
    }

    public static UserResource getUserResource() {
        return new UserResource(getUser1());
    }

    public static User getUser2() {
        return new User("torkit", "1234", "Roman", "Torkit");
    }

    public static UserResource getUserResource2() {
        return new UserResource(getUser2());
    }
}
