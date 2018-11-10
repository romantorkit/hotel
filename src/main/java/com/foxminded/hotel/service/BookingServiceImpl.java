package com.foxminded.hotel.service;

import com.foxminded.hotel.controller.BookingController;
import com.foxminded.hotel.enums.ChargePeriod;
import com.foxminded.hotel.exception_handling.EntityNotFoundException;
import com.foxminded.hotel.model.AdditionalService;
import com.foxminded.hotel.model.Booking;
import com.foxminded.hotel.model.Room;
import com.foxminded.hotel.model.User;
import com.foxminded.hotel.repo.AdditionalServiceRepo;
import com.foxminded.hotel.repo.BookingRepo;
import com.foxminded.hotel.repo.RoomRepo;
import com.foxminded.hotel.resources.BookingResource;
import com.foxminded.hotel.resources.ServiceResource;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final RoomRepo roomRepo;
    private final AdditionalServiceRepo serviceRepo;

    public BookingServiceImpl(BookingRepo bookingRepo,
                              RoomRepo roomRepo,
                              AdditionalServiceRepo serviceRepo) {
        this.bookingRepo = bookingRepo;
        this.roomRepo = roomRepo;
        this.serviceRepo = serviceRepo;
    }

    @Override
    public List<BookingResource> findAll(Optional<User> user) {
        List<ServiceResource> services = convertToServiceResourceList();
        return bookingRepo.findAll()
                .stream()
                .map(booking -> new BookingResource(booking, user, booking.getRoom(), LocalDate.now(), LocalDate.now().plusDays(1L), services))
                .collect(Collectors.toList());
    }

    @Override
    public BookingResource findById(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new EntityNotFoundException(Booking.class, "bookingId", String.valueOf(bookingId)));
        return new BookingResource(booking, Optional.ofNullable(booking.getUser()), booking.getRoom(), LocalDate.now(), LocalDate.now().plusDays(1L), booking.getServices().stream().map(ServiceResource::new).collect(Collectors.toList()));
    }

    @Override
    public BookingResource newBooking(User user, Long roomId, LocalDate start, LocalDate end) {
        List<ServiceResource> services = convertToServiceResourceList();
        return new BookingResource(new Booking(), Optional.ofNullable(user), roomRepo.getOne(roomId), start, end, services);
    }

    @Override
    public BookingResource makeBooking(User user, Long roomId, LocalDate start, LocalDate end, long[] services) {
        List<AdditionalService> list = getServicesList(services);

        Room room = roomRepo.findById(roomId).orElseThrow(() -> new EntityNotFoundException(Room.class, "roomId", String.valueOf(roomId)));
        int amount = calculateAmount(start, end, room.getPrice());

        amount = calculateServicesPrice(amount, start, end, list);

        Booking booking = new Booking(start, end, amount, room, user);
        booking.setServices(list);
        booking = bookingRepo.save(booking);
        return new BookingResource(booking, Optional.ofNullable(user), room, start, end, convertToServiceResourceList(booking.getServices()));
    }

    @Override
    public Resource calculateBookingPrice(Long roomId, LocalDate start, LocalDate end, long[] services) {
        List<AdditionalService> list = getServicesList(services);
        int amount = calculateAmount(start, end, roomRepo.findById(roomId).orElseThrow(() -> new EntityNotFoundException(Room.class, "roomId", String.valueOf(roomId))).getPrice());
        return new Resource<>(calculateServicesPrice(amount, start, end, list)/100, linkTo(methodOn(BookingController.class).create(null, start, end, roomId, services)).withRel("confirm_booking").withTitle("Confirm booking").withType("POST").withMedia("application/json"));
    }

    private int calculateAmount(LocalDate start, LocalDate end, int price) {
        return (int) Duration.between(start.atStartOfDay(), end.atTime(23, 59, 59)).toHours() / 24 * price;
    }

    private int calculateServicesPrice(int amount, LocalDate start, LocalDate end, List<AdditionalService> list) {
        for (AdditionalService service : list) {
            if (service.getChargePeriod().equals(ChargePeriod.DAILY)) {
                amount += calculateAmount(start, end, service.getPrice());
            } else {
                amount += service.getPrice();
            }
        }
        return amount;
    }

    private List<AdditionalService> getServicesList(long[] services) {
        List<AdditionalService> list = new ArrayList<>();
        Arrays.stream(services)
                .flatMap(LongStream::of)
                .forEach(id -> {
                    AdditionalService service = serviceRepo.getOne(id);
                    list.add(service);
                });
        return list;
    }

    private List<ServiceResource> convertToServiceResourceList(){
         return serviceRepo.findAll().stream().map(ServiceResource::new).collect(Collectors.toList());
    }

    private List<ServiceResource> convertToServiceResourceList(List<AdditionalService> services){
        return services.stream().map(ServiceResource::new).collect(Collectors.toList());
    }
}
