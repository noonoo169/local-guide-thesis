package com.example.localguidebe.service;

import com.example.localguidebe.dto.BusyScheduleDTO;
import com.example.localguidebe.entity.BusySchedule;

import java.time.LocalDateTime;
import java.util.List;

public interface BusyScheduleService {
    List<BusyScheduleDTO> addBusySchedule(List<LocalDateTime> busyDates, String email);
    List<BusyScheduleDTO> getBusyScheduleByGuide(String email);
}
