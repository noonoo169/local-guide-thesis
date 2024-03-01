package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.BusyScheduleToBusyScheduleDtoConverter;
import com.example.localguidebe.dto.BusyScheduleDTO;
import com.example.localguidebe.dto.TourDTO;
import com.example.localguidebe.entity.BusySchedule;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.repository.BusyScheduleRepository;
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

  @Autowired
  public BusyScheduleServiceImpl(
      BusyScheduleRepository busyScheduleRepository,
      UserRepository userRepository,
      BusyScheduleToBusyScheduleDtoConverter busyScheduleToBusyScheduleDtoConverter) {
    this.busyScheduleRepository = busyScheduleRepository;
    this.userRepository = userRepository;
    this.busyScheduleToBusyScheduleDtoConverter = busyScheduleToBusyScheduleDtoConverter;
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
  public Set<LocalDate> getBusyDateByTour(TourDTO tourDTO) {
    //Update busy schedule according to tour for users to choose
    Integer cout = 1;
    List<LocalDate> busyDates =
        getBusyScheduleByGuide(tourDTO.getGuide().email()).stream()
            .map(BusyScheduleDTO::busyDate)
            .map(dateTime -> dateTime.toLocalDate())
            .collect(Collectors.toList());
    Set<LocalDate> updatedBusyDates = new HashSet<>();
    //Schedule updates are available by duration of tour
    updatedBusyDates.addAll(busyDates);
    if (tourDTO.getUnit().equals("day(s)")) {
      while (cout < tourDTO.getDuration()) {
        for (LocalDate busyDate : busyDates) {
          updatedBusyDates.add(busyDate.minusDays(cout));
        }
        cout++;
      }
    }
    return updatedBusyDates;
  }
}
