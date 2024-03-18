package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.AddBookingRequestDtoToBookingConverter;
import com.example.localguidebe.converter.CartToCartDtoConverter;
import com.example.localguidebe.dto.CartDTO;
import com.example.localguidebe.dto.requestdto.AddBookingRequestDTO;
import com.example.localguidebe.dto.requestdto.UpdateBookingDTO;
import com.example.localguidebe.entity.Booking;
import com.example.localguidebe.entity.BusySchedule;
import com.example.localguidebe.entity.Cart;
import com.example.localguidebe.entity.Tour;
import com.example.localguidebe.enums.BookingStatusEnum;
import com.example.localguidebe.enums.TypeBusyDayEnum;
import com.example.localguidebe.repository.BookingRepository;
import com.example.localguidebe.repository.CartRepository;
import com.example.localguidebe.repository.TourRepository;
import com.example.localguidebe.repository.UserRepository;
import com.example.localguidebe.service.CartService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {
  private final CartRepository cartRepository;
  private final BookingRepository bookingRepository;
  private final UserRepository userRepository;
  private final AddBookingRequestDtoToBookingConverter addBookingRequestDtoToBookingDtoConverter;
  private final CartToCartDtoConverter cartToCartDtoConverter;
  private final TourRepository tourRepository;

  @Autowired
  public CartServiceImpl(
      CartRepository cartRepository,
      BookingRepository bookingRepository,
      UserRepository userRepository,
      AddBookingRequestDtoToBookingConverter addBookingRequestDtoToBookingDtoConverter,
      CartToCartDtoConverter cartToCartDtoConverter,
      TourRepository tourRepository) {
    this.cartRepository = cartRepository;
    this.bookingRepository = bookingRepository;
    this.userRepository = userRepository;
    this.addBookingRequestDtoToBookingDtoConverter = addBookingRequestDtoToBookingDtoConverter;
    this.cartToCartDtoConverter = cartToCartDtoConverter;
    this.tourRepository = tourRepository;
  }

  @Override
  public Cart getCartByEmail(String email) {
    return cartRepository.getCartByEmail(email).orElse(null);
  }

  @Transactional
  @Override
  public boolean deleteBookingInCartByIdAndByEmail(String email, Long bookingId) {
    Cart cart = getCartByEmail(email);
    if (cart == null) {
      return false;
    }
    boolean bookingDeleteExist =
        cart.getBookings().stream().anyMatch(booking -> booking.getId().equals(bookingId));
    if (!bookingDeleteExist) {
      return false;
    }
    bookingRepository.deleteBookingById(bookingId);
    return true;
  }

  @Transactional
  @Override
  public Cart updateBookingInCart(String email, UpdateBookingDTO updateBookingDTO) {
    Cart cart = getCartByEmail(email);
    if (cart == null) return null;

    Optional<Booking> optionalBooking =
        cart.getBookings().stream()
            .filter(booking -> booking.getId().equals(updateBookingDTO.id()))
            .findFirst();
    if (optionalBooking.isEmpty()) return null;

    Booking booking = optionalBooking.get();
    booking.setNumberTravelers(updateBookingDTO.numberTravelers());
    booking.setStartDate(
        updateBookingDTO.startDate().toLocalDate().atTime(updateBookingDTO.startTime()));
    bookingRepository.save(booking);
    return cart;
  }

  @Override
  public CartDTO addBookingInCart(String email, AddBookingRequestDTO bookingDTO) {
    Integer count = 0;
    Tour tour = tourRepository.findById(bookingDTO.id()).orElseThrow();
    // save busy schedules
    if (tour.getUnit().equals("day(s)")) {
      while (count < tour.getDuration()) {
        tour.getGuide()
            .getBusySchedules()
            .add(
                BusySchedule.builder()
                    .busyDate(bookingDTO.startDate().plusDays(count))
                    .TypeBusyDay(TypeBusyDayEnum.BOOKED_DAY_BY_DAYS)
                    .build());
        count++;
      }
    } else {
      tour.getGuide()
          .getBusySchedules()
          .add(
              BusySchedule.builder()
                  .busyDate(bookingDTO.startDate())
                  .TypeBusyDay(TypeBusyDayEnum.BOOKED_DAY_BY_HOURS)
                  .build());
    }
    tourRepository.save(tour);
    // save booking
    Booking bookingRequest = addBookingRequestDtoToBookingDtoConverter.convert(bookingDTO);
    bookingRequest.setStatus(BookingStatusEnum.PENDING_PAYMENT);
    Booking booking = bookingRepository.save(bookingRequest);
    Cart cart = cartRepository.getCartByTravelerEmail(email).orElse(null);
    // save booking to cart
    if (cart != null) {
      booking.setCart(cart);
      cart.getBookings().add(booking);
      return cartToCartDtoConverter.convert(cartRepository.save(cart), false);
    } else {
      Cart newCart = new Cart();
      newCart.setTraveler(userRepository.findUserByEmail(email));
      newCart = cartRepository.save(newCart);
      booking.setCart(newCart);
      newCart.getBookings().add(booking);
      return cartToCartDtoConverter.convert(cartRepository.save(newCart), false);
    }
  }
}
