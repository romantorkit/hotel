package com.foxminded.hotel.service;

import com.foxminded.hotel.exception_handling.EntityNotFoundException;
import com.foxminded.hotel.model.User;
import com.foxminded.hotel.repo.AdditionalServiceRepo;
import com.foxminded.hotel.repo.BookingRepo;
import com.foxminded.hotel.repo.RoomRepo;
import com.foxminded.hotel.resources.RoomResource;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.foxminded.hotel.service.test_data.AdditionalServicesTestDataFactory.getAllServices;
import static com.foxminded.hotel.service.test_data.BookingTestDataFactory.findBookingsByPeriod;
import static com.foxminded.hotel.service.test_data.RoomTestDataFactory.*;
import static com.foxminded.hotel.service.test_data.UserTestDataFactory.getExistingUser;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class RoomServiceImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private RoomRepo roomRepo;
    @Mock
    private BookingRepo bookingRepo;
    @Mock
    private AdditionalServiceRepo serviceRepo;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    private User existingUser = getExistingUser();

    @Test
    @DisplayName("Test findAvailableRooms returns list RoomResource")
    void findAvailableRooms_validData_returnsFourRooms() {
        User testUser = existingUser;
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(1L);
        List<RoomResource> expected = getAvailableRooms(start, end);

        when(serviceRepo.findAll()).thenReturn(getAllServices());
        when(bookingRepo.findBookingsByPeriod(start, end)).thenReturn(findBookingsByPeriod(start, end));
        when(roomRepo.findAll()).thenReturn(getAllRooms());

        List<RoomResource> actual = roomService.findAvailableRooms(Optional.of(testUser), Optional.empty(), start, end);

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

    }

    @Test
    @DisplayName("Test find room by id returns RoomResource")
    void testFindRoomById_returnsRoomResource() {
        long anyId = 1L;
        User testUser = existingUser;
        RoomResource expected = getRoomResource1();
        when(serviceRepo.findAll()).thenReturn(getAllServices());
        when(roomRepo.findById(anyId)).thenReturn(Optional.of(getRoom1()));

        RoomResource actual = roomService.findRoom(Optional.of(testUser), anyId, LocalDate.now(), LocalDate.now().plusDays(1L));

        assertNotNull(actual);
        assertSame(expected.getRoomNumber(), actual.getRoomNumber());
    }

    @Test
    @DisplayName("Test find room by id throws EntityNotFoundException")
    void testFindRoomById_throwsEntityNotFoundException() {
        long anyId = 1000L;
        User testUser = existingUser;
        when(serviceRepo.findAll()).thenReturn(getAllServices());
        when(roomRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> roomService.findRoom(Optional.of(testUser), anyId, LocalDate.now(), LocalDate.now().plusDays(1L))
        );
    }
}