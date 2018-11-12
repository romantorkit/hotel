package com.foxminded.hotel.service;

import com.foxminded.hotel.exception_handling.EntityNotFoundException;
import com.foxminded.hotel.model.AdditionalService;
import com.foxminded.hotel.model.Booking;
import com.foxminded.hotel.model.Room;
import com.foxminded.hotel.model.User;
import com.foxminded.hotel.repo.AdditionalServiceRepo;
import com.foxminded.hotel.repo.BookingRepo;
import com.foxminded.hotel.repo.RoomRepo;
import com.foxminded.hotel.resources.BookingResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.hateoas.Resource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.foxminded.hotel.service.test_data.AdditionalServicesTestDataFactory.*;
import static com.foxminded.hotel.service.test_data.BookingTestDataFactory.*;
import static com.foxminded.hotel.service.test_data.RoomTestDataFactory.getRoom1;
import static com.foxminded.hotel.service.test_data.UserTestDataFactory.getExistingUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private RoomRepo roomRepo;

    @Mock
    private AdditionalServiceRepo serviceRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private User existingUser = getExistingUser();
    private LocalDate start = LocalDate.now();
    private LocalDate end = LocalDate.now().plusDays(1L);

    @Test
    @DisplayName("Test find all bookings user not empty")
    void testFindAll_returnsBookingResourceList() {
        User testUser = existingUser;
        List<BookingResource> expected = getBookingResourceListByUser(testUser);

        when(serviceRepo.findAll()).thenReturn(getAllServices());
        when(bookingRepo.findAll()).thenReturn(getAllBookingsByUser(testUser));

        List<BookingResource> actual = bookingService.findAll(Optional.of(testUser));

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    @DisplayName("Test find all bookings user empty")
    void testFindAll_userEmpty_returnsBookingResourceList() {
        List<BookingResource> expected = getBookingResourceList();

        when(serviceRepo.findAll()).thenReturn(getAllServices());
        when(bookingRepo.findAll()).thenReturn(getAllBookings());

        List<BookingResource> actual = bookingService.findAll(Optional.empty());

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    @DisplayName("Test find booking by id")
    void testFindById_returnsBookingResource() {
        long anyId = 1L;
        BookingResource expected = getBookingResource1();
        when(bookingRepo.findById(anyLong())).thenReturn(Optional.of(getBooking1()));

        BookingResource actual = bookingService.findById(anyId);

        assertNotNull(actual);
        assertEquals(expected.getBookingId(), actual.getBookingId());
        assertEquals(expected.getRoom().getRoomNumber(), actual.getRoom().getRoomNumber());
        assertEquals(expected.getStart(), actual.getStart());
        assertEquals(expected.getEnd(), actual.getEnd());
        assertEquals(expected.getUser().getUserName(), actual.getUser().getUserName());
    }

    @Test
    @DisplayName("Test find booking by id")
    void testFindById_throwsEntityNotFoundException() {
        long anyId = 1000L;
        when(bookingRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> bookingService.findById(anyId)
        );
    }

    @Test
    @DisplayName("Test new Booking returns BookingResource")
    void testNewBooking_returnsBookingResource() {
        BookingResource expected = getBookingResource1();
        User testUser = existingUser;

        when(serviceRepo.findAll()).thenReturn(getAllServices());
        when(roomRepo.getOne(anyLong())).thenReturn(getRoom1());

        BookingResource actual = bookingService.newBooking(testUser, 1L, LocalDate.now(), LocalDate.now().plusDays(1L));

        assertNotNull(actual);
        assertEquals(expected.getRoom().getRoomNumber(), actual.getRoom().getRoomNumber());
        assertEquals(expected.getUser().getUserName(), actual.getUser().getUserName());
    }

    @Test
    @DisplayName("Test create Booking returns BookingResource")
    void testMakeBooking_returnsBookingResource() {
        Room expectedRoom = getRoom1();
        long[] services = {1, 2};
        User testUser = existingUser;
        BookingResource expected = getBookingResource1();

        when(roomRepo.findById(anyLong())).thenReturn(Optional.of(expectedRoom));
        when(bookingRepo.existsByRoomAndUserAndStartAndEnd(any(Room.class), any(User.class), any(LocalDate.class), any(LocalDate.class))).thenReturn(false);
        when(bookingRepo.save(any(Booking.class))).thenReturn(getBooking1());
        when(serviceRepo.getOne(1L)).thenReturn(getServiceById(1L));
        when(serviceRepo.getOne(2L)).thenReturn(getServiceById(2L));
        when(serviceRepo.findAll()).thenReturn(getServicesForRoom1());

        BookingResource actual = bookingService.makeBooking(testUser, 1L, LocalDate.now(), LocalDate.now().plusDays(1L), services);

        assertNotNull(actual);
        assertSame(expected.getRoom().getRoomNumber(), actual.getRoom().getRoomNumber());
        assertSame(expected.getUser().getUserName(), actual.getUser().getUserName());
        assertEquals(expected.getStart(), actual.getStart());
        assertEquals(expected.getEnd(), actual.getEnd());
    }

    @Test
    @DisplayName("Test calculate booking price")
    void calculateBookingPrice() {

        long[] services = {1, 2};
        int expected = 1250;
        when(serviceRepo.getOne(1L)).thenReturn(getServiceById(1L));
        when(serviceRepo.getOne(2L)).thenReturn(getServiceById(2L));
        when(roomRepo.findById(anyLong())).thenReturn(Optional.of(getRoom1()));

        Resource actual = bookingService.calculateBookingPrice(1L, start, end, services);

        assertNotNull(actual);
        assertEquals(expected, (int)actual.getContent());
    }

    @Test
    @DisplayName("Test get service list")
    void getServicesList() {
        long[] services = {1, 2};
        List<AdditionalService> expected = getServicesForRoom1();
        when(serviceRepo.getOne(1L)).thenReturn(getServiceById(1L));
        when(serviceRepo.getOne(2L)).thenReturn(getServiceById(2L));
        when(roomRepo.findById(anyLong())).thenReturn(Optional.of(getRoom1()));

        List<AdditionalService> actual = bookingService.getServicesList(services);

         assertNotNull(actual);
         assertEquals(expected.size(), actual.size());
         assertSame(expected.get(0).getServiceId(), actual.get(0).getServiceId());
         assertSame(expected.get(1).getServiceId(), actual.get(1).getServiceId());
    }
}