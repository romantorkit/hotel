package com.foxminded.hotel.service;

import com.foxminded.hotel.exception_handling.DuplicateEntryException;
import com.foxminded.hotel.exception_handling.EntityNotFoundException;
import com.foxminded.hotel.exception_handling.WrongCredentialsException;
import com.foxminded.hotel.model.User;
import com.foxminded.hotel.repo.UserRepo;
import com.foxminded.hotel.resources.BookingResource;
import com.foxminded.hotel.resources.UserResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static com.foxminded.hotel.service.test_data.BookingTestDataFactory.*;
import static com.foxminded.hotel.service.test_data.UserTestDataFactory.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    private User existingUser = getExistingUser();
    private User userToLogin = getUserToLogin();
    private User userToRegister = getUserToRegister();

    @Test
    @DisplayName("Test login valid id returns User")
    void testLogin_returnsUser() {
        String username = "tort";
        String password = "1234";
        User expected = userToLogin;

        when(userRepo.findByUserName(anyString())).thenReturn(Optional.of(expected));

        User actual = userService.login(username, password);

        assertNotNull(actual);
        assertEquals(expected.getUserName(), actual.getUserName());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
    }

    @Test
    @DisplayName("Test login throws EntityNotFoundException")
    void testLogin_throwsEntityNotFoundException() {
        String username = "torkit.roman";
        String password = "1234";
        when(userRepo.findByUserName(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.login(username, password)
        );
    }

    @Test
    @DisplayName("Test login throws WrongCredentialsException")
    void testLogin_throwsWrongCredentialsException() {
        String username = "torkit";
        String password = "12345";
        when(userRepo.findByUserName(anyString())).thenReturn(Optional.of(userToRegister));

        assertThrows(WrongCredentialsException.class,
                () -> userService.login(username, password)
        );
    }

    @Test
    @DisplayName("Test getById valid id returns UserResource")
    void testGetById_validId_returnUserResource() {
        long anyId = 1L;
        User expected = existingUser;
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(expected));

        UserResource actual = userService.getById(anyId);

        assertNotNull(actual);
        verify(userRepo).findById(anyId);
        assertSame(expected.getUserId(), actual.getUserId());
    }

    @Test
    @DisplayName("Test getById throws EntityNotFoundException")
    void testGetById_throwsEntityNotFoundException() {
        long anyId = 1000L;
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.getById(anyId)
        );
    }

    @Test
    @DisplayName("Test getBookingsById by userId returns list of BookingResource")
    void testGetBookingsById_validId_returnsListBookingResource() {
        User expected = existingUser;
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(expected));
        existingUser.setBookings(getBookingList(getBooking1(), getBooking2()));

        List<BookingResource> actual = userService.getBookingsById(existingUser.getUserId());

        assertEquals(expected.getBookings().size(), actual.size());
        assertEquals(expected.getBookings().get(0).getRoom().getRoomNumber(), actual.get(0).getRoom().getRoomNumber());
        assertEquals(expected.getBookings().get(1).getRoom().getRoomNumber(), actual.get(1).getRoom().getRoomNumber());
    }

    @Test
    @DisplayName("Test getBookingsById by userId throws EntityNotFoundException")
    void testGetBookingsById_throwsEntityNotFoundException() {
        long anyId = 1000L;
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.getBookingsById(anyId)
        );
    }

    @Test
    @DisplayName("Test create User returns UserResorce")
    void testCreate_returnUserResource() {
        String userName = "torkit";
        String password = "1234";
        String firstName = "Roman";
        String lastName = "Torkit";

        User expected = userToRegister;

        when(userRepo.save(any(User.class))).thenReturn(expected);

        UserResource actual = userService.create(new User(userName, password, firstName, lastName));

        assertNotNull(actual);
        assertEquals(expected.getUserName(), actual.getUserName());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
    }

    @Test
    @DisplayName("Test create User throws DuplicateEntryException")
    void testCreate_throwsDuplicateEntryException() {
        String userName = "tort";
        String password = "1234";
        String firstName = "Roman";
        String lastName = "Torkit";

        User expected = existingUser;

        when(userRepo.findByUserName(anyString())).thenReturn(Optional.of(expected));

        assertThrows(DuplicateEntryException.class,
                () -> userService.create(new User(userName, password, firstName, lastName))
        );
    }
}