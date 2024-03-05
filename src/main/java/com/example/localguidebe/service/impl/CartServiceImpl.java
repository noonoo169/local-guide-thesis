package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.AddBookingRequestDtoToBookingDtoConverter;
import com.example.localguidebe.converter.CartToCartDtoConverter;
import com.example.localguidebe.dto.CartDTO;
import com.example.localguidebe.dto.requestdto.AddBookingRequestDTO;
import com.example.localguidebe.dto.requestdto.UpdateBookingDTO;
import com.example.localguidebe.entity.Booking;
import com.example.localguidebe.entity.Cart;
import com.example.localguidebe.entity.Tour;
import com.example.localguidebe.enums.BookingStatusEnum;
import com.example.localguidebe.repository.BookingRepository;
import com.example.localguidebe.repository.CartRepository;
import com.example.localguidebe.repository.TourRepository;
import com.example.localguidebe.repository.UserRepository;
import com.example.localguidebe.service.BusyScheduleService;
import com.example.localguidebe.service.CartService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {
  private final CartRepository cartRepository;
  private final BookingRepository bookingRepository;

  private final UserRepository userRepository;
  private final AddBookingRequestDtoToBookingDtoConverter addBookingRequestDtoToBookingDtoConverter;
  private final CartToCartDtoConverter cartToCartDtoConverter;
  private final BusyScheduleService busyScheduleService;

  private final TourRepository tourRepository;

  @Autowired
  public CartServiceImpl(
      CartRepository cartRepository,
      BookingRepository bookingRepository,
      UserRepository userRepository,
      AddBookingRequestDtoToBookingDtoConverter addBookingRequestDtoToBookingDtoConverter,
      CartToCartDtoConverter cartToCartDtoConverter,
      BusyScheduleService busyScheduleService,
      TourRepository tourRepository) {
    this.cartRepository = cartRepository;
    this.bookingRepository = bookingRepository;
    this.userRepository = userRepository;
    this.addBookingRequestDtoToBookingDtoConverter = addBookingRequestDtoToBookingDtoConverter;
    this.cartToCartDtoConverter = cartToCartDtoConverter;
    this.busyScheduleService = busyScheduleService;
    this.tourRepository = tourRepository;
  }

  @Override
  public Cart getCartByEmail(String email) {
    return cartRepository.getCartByEmail(email).orElse(null);
  }

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
    bookingRepository.deleteById(bookingId);
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
    booking.setStartDate(updateBookingDTO.startDate().toLocalDate().atTime(updateBookingDTO.startTime()));
    bookingRepository.save(booking);
    return cart;
  }

  @Override
  public CartDTO addBookingInCart(String email, AddBookingRequestDTO bookingDTO) {

    List<LocalDateTime> updatedBusyDates = new ArrayList<>();
    Integer count = 0;
    Tour tour = tourRepository.findById(bookingDTO.id()).orElseThrow();

    List<LocalDateTime> bookingDates =
        bookingRepository.findAll().stream()
            .map(Booking::getStartDate)
            .collect(Collectors.toList());
    // update busy date by duration
    updatedBusyDates.addAll(bookingDates);
    if (!bookingDates.stream()
        .anyMatch(bookingDate -> bookingDate.toLocalDate().equals(bookingDTO.startDate()))) {
      while (count < tour.getDuration()) {
        updatedBusyDates.add(bookingDTO.startDate().plusDays(count));
        count++;
      }
    }
    // Add booking days to guider busy schedule
    busyScheduleService.InsertAndUpdateBusyDates(
        updatedBusyDates, tour.getGuide().getEmail());
    // save booking
    Booking bookingRequest = addBookingRequestDtoToBookingDtoConverter.convert(bookingDTO);
    bookingRequest.setStatus(BookingStatusEnum.PENDING_PAYMENT);
    Booking booking =
        bookingRepository.save(bookingRequest);
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
