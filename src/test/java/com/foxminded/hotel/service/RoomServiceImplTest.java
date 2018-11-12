package com.foxminded.hotel.service;

import com.foxminded.hotel.model.Room;
import com.foxminded.hotel.repo.AdditionalServiceRepo;
import com.foxminded.hotel.repo.BookingRepo;
import com.foxminded.hotel.repo.RoomRepo;
import com.foxminded.hotel.resources.RoomResource;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.foxminded.hotel.service.test_data.RoomTestDataFactory.getAvailableRooms;
import static com.foxminded.hotel.service.test_data.RoomTestDataFactory.getRoom1;
import static com.foxminded.hotel.service.test_data.RoomTestDataFactory.getRoomResource1;
import static com.foxminded.hotel.service.test_data.UserTestDataFactory.getUser1;
import static org.mockito.Mockito.*;

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

    @Spy
    @InjectMocks
    private RoomServiceImpl roomService = new RoomServiceImpl(roomRepo, bookingRepo, serviceRepo);

    @Test
    void findAvailableRooms_validData_returnsFourRooms() {
        List<RoomResource> expected = getAvailableRooms();

//        when(roomRepo.findAll()).thenReturn(expected);

//        List<RoomResource> actual = roomRepo.findAll();
//        List<RoomResource> actual = roomService.findAvailableRooms(Optional.of(getUser1()), Optional.empty(), LocalDate.now(), LocalDate.now().plusDays(1L));


    }

    @Test
    void findRoom() {
        long anyId = 1L;
        RoomResource expected = getRoomResource1();
        when(roomRepo.findById(anyId)).thenReturn(Optional.of(getRoom1()));

        RoomResource actual = roomService.findRoom(Optional.of(getUser1()), anyId, LocalDate.now(), LocalDate.now().plusDays(1L));
    }
}