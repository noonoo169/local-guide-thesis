package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.BusyScheduleToBusyScheduleDtoConverter;
import com.example.localguidebe.dto.BusyScheduleDTO;
import com.example.localguidebe.entity.BusySchedule;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.repository.BusyScheduleRepository;
import com.example.localguidebe.repository.UserRepository;
import com.example.localguidebe.service.BusyScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusyScheduleServiceImpl implements BusyScheduleService {
    private BusyScheduleRepository busyScheduleRepository;
    private final BusyScheduleToBusyScheduleDtoConverter busyScheduleToBusyScheduleDtoConverter;
    private UserRepository userRepository;
    @Autowired
    public BusyScheduleServiceImpl(BusyScheduleRepository busyScheduleRepository,UserRepository userRepository,BusyScheduleToBusyScheduleDtoConverter busyScheduleToBusyScheduleDtoConverter){
        this.busyScheduleRepository = busyScheduleRepository;
        this.userRepository = userRepository;
        this.busyScheduleToBusyScheduleDtoConverter = busyScheduleToBusyScheduleDtoConverter;
    }


    @Override
    public List<BusyScheduleDTO> addBusySchedule(List<LocalDateTime> busyDates, String email) {
        List<BusySchedule> busySchedules = new ArrayList<>();
        User guide = userRepository.findUserByEmail(email);

        busyDates.forEach(busyDate->{
            BusySchedule busySchedule = new BusySchedule();
            busySchedule.setBusyDate(busyDate);
            busySchedule.setGuide(guide);
            busySchedules.add(busySchedule);
        });
        busyScheduleRepository.saveAll(busySchedules);

        return busyScheduleRepository.findAllByGuideId(guide.getId()).stream().map(busyScheduleToBusyScheduleDtoConverter::convert).collect(Collectors.toList());
    }

    @Override
    public List<BusyScheduleDTO> getBusyScheduleByGuide(String email) {
        User guide = userRepository.findUserByEmail(email);
        return busyScheduleRepository.findAllByGuideId(guide.getId()).stream().map(busyScheduleToBusyScheduleDtoConverter::convert).collect(Collectors.toList());
    }
}
