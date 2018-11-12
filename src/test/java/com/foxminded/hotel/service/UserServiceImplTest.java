package com.foxminded.hotel.service;

import com.foxminded.hotel.HotelApplication;
import com.foxminded.hotel.model.User;
import com.foxminded.hotel.repo.UserRepo;
import com.foxminded.hotel.resources.UserResource;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.Optional;

import static com.foxminded.hotel.service.test_data.UserTestDataFactory.getUser1;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@SpringJUnitWebConfig(HotelApplication.class)
class UserServiceImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @MockBean
    private UserRepo userRepo;

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setup(){
        userService = new UserServiceImpl(userRepo);
    }

    private User testUser = getUser1();

    @Test
    void login() {
    }

    @Test
    @DisplayName("Get user by id")
    void getById() {
        long anyId = 1;
        Mockito.when(userRepo.findById(anyId)).thenReturn(Optional.of(testUser));

        UserResource actual = userService.getById(anyId);

        Mockito.verify(userRepo).findById(anyId);
        assertSame(testUser.getUserId(), actual.getUserId());
    }

    @Test
    void getBookingsById() {
    }

    @Test
    void create() {
    }
}