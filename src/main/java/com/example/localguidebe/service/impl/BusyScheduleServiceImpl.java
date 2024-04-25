package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.BusyScheduleToBusyScheduleDtoConverter;
import com.example.localguidebe.dto.BusyScheduleDTO;
import com.example.localguidebe.dto.responsedto.BusyScheduleOfGuiderResponseDTO;
import com.example.localguidebe.entity.Booking;
import com.example.localguidebe.entity.BusySchedule;
import com.example.localguidebe.entity.Tour;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.repository.BookingRepository;
import com.example.localguidebe.repository.BusyScheduleRepository;
import com.example.localguidebe.repository.TourRepository;
import com.example.localguidebe.repository.UserRepository;
import com.example.localguidebe.service.BusyScheduleService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusyScheduleServiceImpl implements BusyScheduleService {
  private BusyScheduleRepository busyScheduleRepository;
  private final BusyScheduleToBusyScheduleDtoConverter busyScheduleToBusyScheduleDtoConverter;
  private UserRepository userRepository;

  private TourRepository tourRepository;
  private BookingRepository bookingRepository;

  @Autowired
  public BusyScheduleServiceImpl(
      BusyScheduleRepository busyScheduleRepository,
      UserRepository userRepository,
      BusyScheduleToBusyScheduleDtoConverter busyScheduleToBusyScheduleDtoConverter,
      TourRepository tourRepository,
      BookingRepository bookingRepository) {
    this.busyScheduleRepository = busyScheduleRepository;
    this.userRepository = userRepository;
    this.busyScheduleToBusyScheduleDtoConverter = busyScheduleToBusyScheduleDtoConverter;
    this.tourRepository = tourRepository;
    this.bookingRepository = bookingRepository;
  }

  @Override
  public List<BusyScheduleDTO> InsertAndUpdateBusyDates(
      List<LocalDateTime> busyDates, String email) {
    List<BusySchedule> busySchedules = new ArrayList<>();
    User guide = userRepository.findUserByEmail(email);

    busyDates.forEach(
        busyDate -> {
          BusySchedule busySchedule = new BusySchedule();
          busySchedule.setBusyDate(busyDate);
          busySchedule.setGuide(guide);
          busySchedules.add(busySchedule);
        });
    busyScheduleRepository.deleteAll();
    busyScheduleRepository.saveAll(busySchedules);

    return busyScheduleRepository.findAllByGuideId(guide.getId()).stream()
        .map(busyScheduleToBusyScheduleDtoConverter::convert)
        .collect(Collectors.toList());
  }

  @Override
  public List<BusyScheduleDTO> getBusyScheduleByGuide(String email) {
    User guide = userRepository.findUserByEmail(email);
    return busyScheduleRepository.findAllByGuideId(guide.getId()).stream()
        .map(busyScheduleToBusyScheduleDtoConverter::convert)
        .collect(Collectors.toList());
  }

  @Override
  public Set<LocalDate> getBusyDateByTour(Long tourId) {
    // Update busy schedule according to tour for users to choose
    Integer count = 1;
    Tour tour = tourRepository.findById(tourId).orElseThrow();

    List<LocalDate> busyDates =
        getBusyScheduleByGuide(tour.getGuide().getEmail()).stream()
            .map(BusyScheduleDTO::busyDate)
            .map(dateTime -> dateTime.toLocalDate())
            .collect(Collectors.toList());
    Set<LocalDate> updatedBusyDates = new HashSet<>();
    // Schedule updates are available by duration of tour
    updatedBusyDates.addAll(busyDates);
    if (tour.getUnit().equals("day(s)")) {
      while (count < tour.getDuration()) {
        for (LocalDate busyDate : busyDates) {
          updatedBusyDates.add(busyDate.minusDays(count));
        }
        count++;
      }
    }
    return updatedBusyDates;
  }

  @Override
  public BusyScheduleOfGuiderResponseDTO getBusySchedulesAndPreBookedSchedules(String email) {
    BusyScheduleOfGuiderResponseDTO busyScheduleOfGuiderResponseDTO =
        new BusyScheduleOfGuiderResponseDTO();
    int count = 1;
    List<Booking> bookings = bookingRepository.getAllBookingByGuider(email);
    for (Booking booking : bookings) {
      busyScheduleOfGuiderResponseDTO.getBusyDayByBooking().add(booking.getStartDate());
      if (booking.getTour().getUnit() == "day(s)") {
        while (count < booking.getTour().getDuration()) {
          busyScheduleOfGuiderResponseDTO
              .getBusyDayByBooking()
              .add(booking.getStartDate().plusDays(count));
          count++;
        }
      }
      count = 1;
    }
    busyScheduleOfGuiderResponseDTO.setBusyDayOfGuider(
        getBusyScheduleByGuide(email).stream()
            .map(BusyScheduleDTO::busyDate)
            .collect(Collectors.toList()));
    busyScheduleOfGuiderResponseDTO
        .getBusyDayOfGuider()
        .removeAll(busyScheduleOfGuiderResponseDTO.getBusyDayByBooking());
    return busyScheduleOfGuiderResponseDTO;
  }
}
